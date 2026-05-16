package br.unitins.topicos1.prancha.service;

import java.util.List;
import br.unitins.topicos1.prancha.dto.ItemPedidoDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.ItemPedido;
import br.unitins.topicos1.prancha.model.Pedido;
import br.unitins.topicos1.prancha.model.Prancha;
import br.unitins.topicos1.prancha.repository.ItemPedidoRepository;
import br.unitins.topicos1.prancha.repository.PedidoRepository;
import br.unitins.topicos1.prancha.repository.PranchaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Inject
    ItemPedidoRepository itemPedidoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    PranchaRepository pranchaRepository;

    // método para buscar o pedido no banco, validar o id e garantir que o pedido
    // exista antes de criar/atualizar um item
    private Pedido buscarPedido(Long idPedido) {

        // validações nos campos obrigatórios
        if (idPedido == null || idPedido <= 0) {
            throw ValidationException.of("idPedido", "ID de Pedido é obrigatório e válido.");
        }
        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null) {
            throw ValidationException.of("idPedido", "Pedido não encontrado.");
        }
        return pedido;
    }

    // método para buscar a prancha no banco, validar o id e garantir que a prancha
    // exista antes de criar/atualizar um itemn
    private Prancha buscarPrancha(Long idPrancha) {

        // validações nos campos obrigatórios
        if (idPrancha == null || idPrancha <= 0) {
            throw ValidationException.of("idPrancha", "ID de Prancha é obrigatório e válido.");
        }
        Prancha prancha = pranchaRepository.findById(idPrancha);
        if (prancha == null) {
            throw ValidationException.of("idPrancha", "Prancha não encontrada.");
        }
        return prancha;
    }

    // busca todos os registros no banco
    @Override
    public List<ItemPedido> findAll() {
        List<ItemPedido> listaItens = itemPedidoRepository.listAll();
        if (listaItens.isEmpty()) {
            throw ValidationException.of("Lista de Itens", "Nenhum item de pedido cadastrado.");
        }
        return listaItens;
    }

    // busca todos os registros pelo pedido no banco
    @Override
    public List<ItemPedido> findByPedido(Long idPedido) {
        if (idPedido == null || idPedido <= 0) {
            throw ValidationException.of("idPedido", "id de Pedido inválido.");
        }

        List<ItemPedido> listaItens = itemPedidoRepository.findByPedido(idPedido);
        if (listaItens.isEmpty()) {
            throw ValidationException.of("idPedido", "Nenhum item encontrado para o Pedido informado.");
        }
        return listaItens;
    }

    // busca todos os registros pelo id no banco
    @Override
    public ItemPedido findById(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido.");
        }

        ItemPedido itemPedido = itemPedidoRepository.findById(id);
        if (itemPedido == null) {
            throw ValidationException.of("id", "Item de Pedido não encontrado.");
        }
        return itemPedido;
    }

    // criando um item
    @Override
    @Transactional
    public ItemPedido create(Long idPedido, @Valid ItemPedidoDTO dto) {

        // validações nos campos obrigatórios
        if (dto == null) {
            throw ValidationException.of("dto", "Dados do Item de Pedido são obrigatórios.");
        }

        // buscando através dos métodos
        Pedido pedido = buscarPedido(idPedido);
        Prancha prancha = buscarPrancha(dto.idPrancha());

        // cria o item
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);
        itemPedido.setPrancha(prancha);
        itemPedido.setQuantidade(dto.quantidade());
        itemPedido.setPrecoUnit(prancha.getValor());
        itemPedido.setSubTotal(prancha.getValor() * dto.quantidade()); // calcula o subtotal

        itemPedidoRepository.persist(itemPedido);

        return itemPedido;
    }

    // alterando um item
    @Override
    @Transactional
    public void update(Long idItem, Long idPedido, @Valid ItemPedidoDTO dto) {

        // validações nos campos obrigatórios
        ItemPedido item = itemPedidoRepository.findById(idItem);
        if (item == null) {
            throw ValidationException.of("id", "Item de Pedido não encontrado.");
        }

        // buscando através dos métodos
        Pedido pedido = buscarPedido(idPedido);
        Prancha prancha = buscarPrancha(dto.idPrancha());

        // alterando os campos
        item.setPedido(pedido);
        item.setPrancha(prancha);
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnit(prancha.getValor());
        item.setSubTotal(prancha.getValor() * dto.quantidade()); // recalcula o subtotal
    }

    // deletando um item
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido.");
        }

        ItemPedido itemPedido = itemPedidoRepository.findById(id);
        if (itemPedido == null) {
            throw ValidationException.of("id", "Item de Pedido não encontrado.");
        }

        // deleta o item no banco
        itemPedidoRepository.delete(itemPedido);
    }

}