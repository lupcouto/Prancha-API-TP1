package br.unitins.topicos1.prancha.service;
import java.util.List;
import java.util.stream.Collectors;
import br.unitins.topicos1.prancha.dto.PixDTO;
import br.unitins.topicos1.prancha.dto.PixResponseDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Pix;
import br.unitins.topicos1.prancha.repository.PixRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class PixServiceImpl implements PixService {

    @Inject
    PixRepository pixRepository;

    // buscando todos os registros no banco, convertendo para o response e retornando uma lista
    @Override
    public List<PixResponseDTO> findAll() {
        List<Pix> listaPix = pixRepository.listAll();
        if (listaPix.isEmpty()) {
            throw ValidationException.of("listaPix", "Nenhum pix cadastrado");
        }

        return listaPix.stream().map(PixResponseDTO::new).collect(Collectors.toList());
    }

    // buscando todos os registros pela chave no banco, convertendo para o response e retornando uma lista
    @Override
    public List<PixResponseDTO> findByChave(String chave) {
        if (chave == null || chave.isBlank()) {
            throw ValidationException.of("chave", "A chave é obrigatório");
        }

        List<Pix> listaPix = pixRepository.findByChave(chave);
        if (listaPix.isEmpty()) {
            throw ValidationException.of("chave", "Nenhum pix encontrado com essa chave");
        }

        return listaPix.stream().map(PixResponseDTO::new).collect(Collectors.toList());
    }

    // buscando todos os registros pelo id no banco
    @Override
    public PixResponseDTO findById(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Pix pixEntity = pixRepository.findById(id);
        if (pixEntity == null) {
            throw ValidationException.of("id", "Pix não encontrado");
        }

        return new PixResponseDTO(pixEntity);
    }

    // criando um pix
    @Override
    @Transactional
    public PixResponseDTO create(@Valid PixDTO dto) {

        // validações nos campos obrigatórios
        if (dto == null) {
            throw ValidationException.of("dto", "Dados do pix são obrigatórios");
        }

        // cria o pix
        Pix pixEntity = new Pix();
        pixEntity.setChave(dto.chave());


        // defindo esses atributos como null
        pixEntity.setDataPagamento(null);
        pixEntity.setStatusPagamento(null);

        pixRepository.persist(pixEntity);

        return new PixResponseDTO(pixEntity);
    }

    // alterando um pix
    @Override
    @Transactional
    public void update(Long id, @Valid PixDTO dto) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        if (dto == null) {
            throw ValidationException.of("dto", "Dados do pix são obrigatórios");
        }

        Pix pixEntity = pixRepository.findById(id);
        if (pixEntity == null) {
            throw ValidationException.of("id", "Pix não encontrado");
        }

        // alterando o campo
        pixEntity.setChave(dto.chave());
    }

    // deletando um pix
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Pix pixEntity = pixRepository.findById(id);

        if (pixEntity == null) {
            throw ValidationException.of("id", "Pix não encontrado");
        }

        // deleta o pix encontrado
        pixRepository.delete(pixEntity);
    }
    
}
