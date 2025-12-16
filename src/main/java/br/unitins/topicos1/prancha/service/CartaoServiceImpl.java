package br.unitins.topicos1.prancha.service;
import java.util.List;
import java.util.stream.Collectors;
import br.unitins.topicos1.prancha.dto.CartaoDTO;
import br.unitins.topicos1.prancha.dto.CartaoResponseDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Cartao;
import br.unitins.topicos1.prancha.repository.CartaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class CartaoServiceImpl implements CartaoService {

    @Inject
    CartaoRepository cartaoRepository;

    // buscando todos os registros no banco, convertendo para o response e retornando uma lista
    @Override
    public List<CartaoResponseDTO> findAll() {
        List<Cartao> listaCartoes = cartaoRepository.listAll();
        if (listaCartoes.isEmpty()) {
            throw ValidationException.of("listaCartoes", "Nenhum cartão cadastrado");
        }

        return listaCartoes.stream().map(CartaoResponseDTO::new).collect(Collectors.toList());
    }

    // buscando todos os registros pelo número no banco, convertendo para o response e retornando uma lista
    @Override
    public List<CartaoResponseDTO> findByNumeroCartao(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.isBlank()) {
            throw ValidationException.of("numeroCartao", "Número do cartão é obrigatório");
        }

        List<Cartao> listaCartoes = cartaoRepository.findByNumeroCartao(numeroCartao);
        if (listaCartoes.isEmpty()) {
            throw ValidationException.of("numeroCartao", "Nenhum cartão encontrado com esse número");
        }

        return listaCartoes.stream().map(CartaoResponseDTO::new).collect(Collectors.toList());
    }

    // buscando todos os registros pelo id no banco
    @Override
    public CartaoResponseDTO findById(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Cartao cartaoEntity = cartaoRepository.findById(id);
        if (cartaoEntity == null) {
            throw ValidationException.of("id", "Cartão não encontrado");
        }

        return new CartaoResponseDTO(cartaoEntity);
    }

    // criando um cartão
    @Override
    @Transactional
    public CartaoResponseDTO create(@Valid CartaoDTO dto) {

        // validações nos campos obrigatórios
        if (dto == null) {
            throw ValidationException.of("dto", "Dados do cartão são obrigatórios");
        }

        // cria um cartão
        Cartao cartaoEntity = new Cartao();
        cartaoEntity.setNumeroCartao(dto.numeroCartao());
        cartaoEntity.setNomeTitular(dto.nomeTitular());
        cartaoEntity.setDataVencimento(dto.dataVencimento());

        // definindo esses atributos como null
        cartaoEntity.setDataPagamento(null);
        cartaoEntity.setStatusPagamento(null);

        cartaoRepository.persist(cartaoEntity);

        return new CartaoResponseDTO(cartaoEntity);
    }

    // alterando um cartão
    @Override
    @Transactional
    public void update(Long id, @Valid CartaoDTO dto) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        if (dto == null) {
            throw ValidationException.of("dto", "Dados do cartão são obrigatórios");
        }

        Cartao cartaoEntity = cartaoRepository.findById(id);
        if (cartaoEntity == null) {
            throw ValidationException.of("id", "Cartão não encontrado");
        }

        // alterando os campos
        cartaoEntity.setNumeroCartao(dto.numeroCartao());
        cartaoEntity.setNomeTitular(dto.nomeTitular());
        cartaoEntity.setDataVencimento(dto.dataVencimento());
    }

    // deletando um cxrtão
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Cartao cartaoEntity = cartaoRepository.findById(id);

        if (cartaoEntity == null) {
            throw ValidationException.of("id", "Cartão não encontrado");
        }

        // deleta o cartão encontrado
        cartaoRepository.delete(cartaoEntity);
    }
    
}