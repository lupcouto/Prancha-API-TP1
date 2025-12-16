package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.EnderecoDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Endereco;
import br.unitins.topicos1.prancha.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    // busca todos os registros no banco
    @Override
    public List<Endereco> findAll() {
        List<Endereco> listaEnderecos = enderecoRepository.listAll();
        if (listaEnderecos.isEmpty()) {
            throw ValidationException.of("Lista de Endereços", "Nenhum endereço cadastrado");
        }

        return listaEnderecos;
    }

    // busca todos os registros pelo cep no banco
    @Override
    public List<Endereco> findByCep(String cep) {

        // validações nos campos obrigatórios
        if (cep == null || cep.isBlank()) {
            throw ValidationException.of("cep", "O CEP é obrigatório");
        }

        List<Endereco> listaEnderecos = enderecoRepository.findByCep(cep);
        if (listaEnderecos.isEmpty()) {
            throw ValidationException.of("cep", "Nenhum endereço encontrado para o CEP informado");
        }

        return listaEnderecos;
    }

    // busca todos os registros pelo id no banco
    @Override
    public Endereco findById(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw ValidationException.of("id", "Endereço não encontrado");
        }

        return endereco;
    }

    // criando um endereço
    @Override
    @Transactional
    public Endereco create(@Valid EnderecoDTO dto) {

        // validações nos campos obrigatórios
        if (dto == null) {
            throw ValidationException.of("endereco", "Dados do endereço são obrigatórios");
        }

        if (dto.cidade() == null || dto.cidade().isBlank()) {
            throw ValidationException.of("cidade", "A cidade é obrigatória");
        }

        if (dto.estado() == null || dto.estado().isBlank()) {
            throw ValidationException.of("estado", "O estado é obrigatório");
        }

        if (dto.cep() == null || dto.cep().isBlank()) {
            throw ValidationException.of("cep", "O CEP é obrigatório");
        }

        // cria o endereço
        Endereco endereco = new Endereco();
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());

        enderecoRepository.persist(endereco);

        return endereco;
    }

    // alterando um endereço
    @Override
    @Transactional
    public void update(Long id, @Valid EnderecoDTO dto) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw ValidationException.of("id", "Endereço não encontrado");
        }

        // alterando os campos
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
    }

    // deletando um endereço
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw ValidationException.of("id", "Endereço não encontrado");
        }

        // deleta o endereço encontrado
        enderecoRepository.delete(endereco);
    }
    
}