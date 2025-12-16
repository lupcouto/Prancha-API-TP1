package br.unitins.topicos1.prancha.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;
import br.unitins.topicos1.prancha.dto.*;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.*;
import br.unitins.topicos1.prancha.repository.ClienteRepository;
import br.unitins.topicos1.prancha.repository.PedidoRepository;
import br.unitins.topicos1.prancha.repository.PranchaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    private static final Logger LOG = Logger.getLogger(PedidoServiceImpl.class);

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    PranchaRepository pranchaRepository;

    // método para criar todo o pagamento
    private Pagamento criarPagamento(PedidoDTO dto) {

        // verifica se a forma de pagamento foi enviada
        if (dto.formaPagamento() == null || dto.formaPagamento().isBlank()) {
            LOG.error("Forma de pagamento não informada.");
            throw ValidationException.of("formaPagamento", "A forma de pagamento deve ser informada.");
        }

        String forma = dto.formaPagamento().toUpperCase();
        Pagamento pagamento;

        // switch para criar o pagamento de acordo com o que o cliente escolher
        switch (forma) {
            case "PIX" -> { // se escolher pix, preenche os campos do pix
                if (dto.pix() == null) {
                    LOG.error("Dados PIX não enviados.");
                    throw ValidationException.of("pix", "Dados do PIX devem ser enviados.");
                }
                Pix pix = new Pix();
                pix.setChave(dto.pix().chave());
                pagamento = pix;
            }
            case "BOLETO" -> { // se escolher boleto, preenche os campos do boleto
                if (dto.boleto() == null) {
                    LOG.error("Dados do boleto não enviados.");
                    throw ValidationException.of("boleto", "Dados do boleto devem ser enviados.");
                }
                Boleto boleto = new Boleto();
                boleto.setCodigoBarras(dto.boleto().codigoBarras());
                boleto.setDataVencimento(dto.boleto().dataVencimento());
                pagamento = boleto;
            }
            case "CARTAO" -> { // se escolher cartão, preenche os campos do cartão
                if (dto.cartao() == null) {
                    LOG.error("Dados do cartão não enviados.");
                    throw ValidationException.of("cartao", "Dados do cartão devem ser enviados.");
                }
                Cartao cartao = new Cartao();
                cartao.setNumeroCartao(dto.cartao().numeroCartao());
                cartao.setNomeTitular(dto.cartao().nomeTitular());
                cartao.setDataVencimento(dto.cartao().dataVencimento());
                pagamento = cartao;
            }
            default -> { // se escolher outra forma, erro
                LOG.error("Forma de pagamento inválida: " + forma);
                throw ValidationException.of("formaPagamento", "Forma de pagamento inválida. Use PIX, BOLETO ou CARTAO.");
            }
        }

        // define "pendente" como padrão até o pedido ser pago
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        LOG.info("Pagamento criado com status PENDENTE.");
        return pagamento;
    }

    // buscando todos os registros no banco, convertendo para o response e retornando uma lista
    @Override
    public List<PedidoResponseDTO> findAll() {
        LOG.info("Buscando todos os pedidos...");
        List<Pedido> listaPedidos = pedidoRepository.listAll();

        if (listaPedidos.isEmpty()) {
            LOG.warn("Nenhum pedido encontrado.");
            throw ValidationException.of("listaPedidos", "Nenhum pedido cadastrado");
        }

        LOG.info(listaPedidos.size() + " pedidos encontrados.");
        return listaPedidos.stream().map(this::toResponse).toList();
    }

    // buscando o histórico de pedidos de um cliente no banco e retornando uma lista
    @Override
    public List<PedidoResponseDTO> findByCliente(Long idCliente) {
        LOG.info("Buscando pedidos do cliente ID: " + idCliente);

        Cliente cliente = clienteRepository.findById(idCliente);
        if (cliente == null) {
            LOG.error("Cliente não encontrado ID: " + idCliente);
            throw ValidationException.of("idCliente", "Cliente não encontrado");
        }

        return pedidoRepository.findByCliente(cliente).stream().map(this::toResponse).toList();
    }

    // buscando todos os registros pelo id no banco
    @Override
    public PedidoResponseDTO findById(Long id) {
        LOG.info("Buscando pedido por ID: " + id);

        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            LOG.error("Pedido não encontrado ID: " + id);
            throw ValidationException.of("id", "Pedido não encontrado");
        }

        return toResponse(pedido);
    }

    // criando um pedido
    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Criando novo pedido...");

        if (dto == null) {
            LOG.error("DTO do pedido está nulo.");
            throw ValidationException.of("dto", "Dados do pedido são obrigatórios");
        }

        Cliente cliente = clienteRepository.findById(dto.idCliente());
        if (cliente == null) {
            LOG.error("Cliente não encontrado ID: " + dto.idCliente());
            throw ValidationException.of("idCliente", "Cliente não encontrado");
        }

        LOG.info("Cliente encontrado: " + cliente.getNome());

        // cria o pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());

        // cria o endereço do pedido
        Endereco end = new Endereco();
        end.setCidade(dto.endereco().cidade());
        end.setEstado(dto.endereco().estado());
        end.setCep(dto.endereco().cep());
        pedido.setEndereco(end);

        // chama o método para criar o pagamento e associar ao pedido
        Pagamento pagamento = criarPagamento(dto);
        pedido.setPagamento(pagamento);

        List<ItemPedido> itens = new ArrayList<>(); // criando uma lista pra armazenar cada item
        double total = 0; // variável pra guardar o valor total do pedido

        // para cada item que o cliente requisitou
        for (ItemPedidoDTO itemDTO : dto.itens()) {

            // buscar a prancha que o cliente quer comprar
            Prancha prancha = pranchaRepository.findById(itemDTO.idPrancha());
            if (prancha == null) {
                LOG.error("Prancha não encontrada ID: " + itemDTO.idPrancha());
                throw ValidationException.of("idPrancha", "Prancha não encontrada");
            }

            LOG.info("Item adicionado → Prancha: " + prancha.getTipoPrancha()
                    + " | Qtd: " + itemDTO.quantidade());

            // se a prancha existir, cria o item do pedido
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setPrancha(prancha);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnit(prancha.getValor());
            item.setSubTotal(prancha.getValor() * itemDTO.quantidade()); // calcula o subtotal

            total += item.getSubTotal(); // acumula o valor do subtotal
            itens.add(item); // adiciona o item na lista
        }

        // associa os itens ao pedido
        pedido.setItens(itens);
        pedido.setValorTotal(total);

        // cria o pedido no banco
        pedidoRepository.persist(pedido);

        LOG.info("Pedido criado com sucesso! ID: " + pedido.getId()
                + " | Total: R$ " + pedido.getValorTotal());

        return toResponse(pedido);
    }

    // alterando um pedido
    @Override
    @Transactional
    public void update(Long id, @Valid PedidoDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Atualizando pedido ID: " + id);

        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            LOG.error("Pedido não encontrado ID: " + id);
            throw ValidationException.of("id", "Pedido não encontrado");
        }

        // altera o endereço
        Endereco end = pedido.getEndereco();
        end.setCidade(dto.endereco().cidade());
        end.setEstado(dto.endereco().estado());
        end.setCep(dto.endereco().cep());

        // altera a forma de pagamento
        pedido.setPagamento(criarPagamento(dto));

        LOG.info("Pedido atualizado com sucesso ID: " + id);
    }

    // deletando um pedido
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        LOG.info("Deletando pedido ID: " + id);

        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            LOG.error("Pedido não encontrado ID: " + id);
            throw ValidationException.of("id", "Pedido não encontrado");
        }

        pedidoRepository.delete(pedido);
        LOG.info("Pedido deletado com sucesso ID: " + id);
    }

    // método para converter a entidade em um DTO de resposta
    private PedidoResponseDTO toResponse(Pedido pedido) {

        // seleciona o pagamento associado ao pedido
        Pagamento pag = pedido.getPagamento();

        // testa qual forma de pagamento foi escolhida. A que for, mostra o DTO, as que não forem, mostra tudo null
        PixResponseDTO pixDTO = (pag instanceof Pix p) ? new PixResponseDTO(p) : null;
        BoletoResponseDTO boletoDTO = (pag instanceof Boleto b) ? new BoletoResponseDTO(b) : null;
        CartaoResponseDTO cartaoDTO = (pag instanceof Cartao c) ? new CartaoResponseDTO(c) : null;

        // exibe o cliente com seus dados no formato DTO
        ClienteDTO clienteDTO = new ClienteDTO(
            pedido.getCliente().getNome(),
            pedido.getCliente().getTelefone().getDdd(),
            pedido.getCliente().getTelefone().getNumero(),
            pedido.getCliente().getCpf()
        );

        // exibe o endereço com seus dados no formato DTO
        EnderecoDTO enderecoDTO = new EnderecoDTO(
            pedido.getEndereco().getCidade(),
            pedido.getEndereco().getEstado(),
            pedido.getEndereco().getCep()
        );

        // exibe a lista de itens com os seus dados no formato DTO
        List<ItemPedidoDTO> itensDTO = pedido.getItens().stream()
            .map(item -> new ItemPedidoDTO(
                item.getPrancha().getId(),
                item.getQuantidade(),
                item.getPrecoUnit()
            ))
            .toList();

        // exibe o response de pedido e o seu corpo no formato DTO
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getDataPedido(),
            pedido.getValorTotal(),
            clienteDTO,
            enderecoDTO,
            pag.getClass().getSimpleName(),
            pixDTO,
            boletoDTO,
            cartaoDTO,
            itensDTO
        );
    }

    // simulando um pagamento manual
    @Override
    @Transactional
    public void pagar(Long id) {

        // validações nos campos obrigatórios
        LOG.info("Processando pagamento do pedido ID: " + id);

        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            LOG.error("Pedido não encontrado ID: " + id);
            throw ValidationException.of("id", "Pedido não encontrado");
        }

        Pagamento pagamento = pedido.getPagamento();
        pagamento.setStatusPagamento(StatusPagamento.PAGO); // marca o pagamento como "pago"
        pagamento.setDataPagamento(LocalDateTime.now()); // registra a data e a hora

        pedidoRepository.persist(pedido);

        LOG.info("Pagamento confirmado! Pedido ID: " + id);
    }

    // encerrando o pedido
    @Override
    @Transactional
    public void finalizar(Long idPedido) {

        // validações nos campos obrigatórios
        LOG.info("Finalizando pedido ID: " + idPedido);

        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null) {
            LOG.error("Pedido não encontrado ID: " + idPedido);
            throw ValidationException.of("idPedido", "Pedido não encontrado");
        }

        // para cada item
        for (ItemPedido item : pedido.getItens()) {
            Prancha prancha = item.getPrancha();

            LOG.info("Verificando estoque da prancha: " + prancha.getTipoPrancha());

            // verifica se tem estoque suficiente para aquela prancha
            if (prancha.getEstoque() < item.getQuantidade()) {
                LOG.error("Estoque insuficiente! Prancha: "
                        + prancha.getTipoPrancha());
                throw ValidationException.of("estoque",
                        "Prancha " + prancha.getTipoPrancha() + " não tem estoque suficiente!");
            }

            // se o estoque for suficiente
            prancha.setEstoque(prancha.getEstoque() - item.getQuantidade()); // diminuir o estoque
            pranchaRepository.persist(prancha); // salvar o novo estoque da prancha no banco

            LOG.info("Estoque atualizado → Prancha: " + prancha.getTipoPrancha()
                    + " | Novo estoque: " + prancha.getEstoque());
        }

        // atualiza as informações
        pedido.getPagamento().setStatusPagamento(StatusPagamento.PAGO);
        pedido.getPagamento().setDataPagamento(LocalDateTime.now());

        pedidoRepository.persist(pedido);

        LOG.info("Pedido finalizado com sucesso ID: " + idPedido);
    }
}