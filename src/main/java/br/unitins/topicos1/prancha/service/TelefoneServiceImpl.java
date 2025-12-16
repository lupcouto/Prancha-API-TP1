package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.TelefoneDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Telefone;
import br.unitins.topicos1.prancha.repository.TelefoneRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {

    @Inject
    TelefoneRepository telefoneRepository;

    // busca todos os registros no banco
    @Override
    public List<Telefone> findAll() {
        List<Telefone> listaTelefones = telefoneRepository.listAll();
        if (listaTelefones.isEmpty()) {
            throw ValidationException.of("Lista de Telefones", "Nenhum telefone cadastrado");
        }

        return listaTelefones;
    }

    // busca todos os registros pelo número no banco
    @Override
    public List<Telefone> findByNumero(String numero) {

        // validações nos campos obrigatórios
        if (numero == null || numero.isBlank()) {
            throw ValidationException.of("numero", "Número de telefone é obrigatório");
        }

        List<Telefone> listaTelefones = telefoneRepository.findByNumero(numero);
        if (listaTelefones.isEmpty()) {
            throw ValidationException.of("numero", "Nenhum telefone encontrado com o número informado");
        }

        return listaTelefones;
    }

    // busca todos os registros pelo id no banco
    @Override
    public Telefone findById(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Telefone telefone = telefoneRepository.findById(id);
        if (telefone == null) {
            throw ValidationException.of("id", "Telefone não encontrado");
        }

        return telefone;
    }

    // criando um telefone
    @Override
    @Transactional
    public Telefone create(TelefoneDTO dto) {

        // validações nos campos obrigatórios
        if (dto == null) {
            throw ValidationException.of("dto", "Dados do telefone são obrigatórios");
        }

        if (dto.ddd() == null || dto.ddd().isBlank()) {
            throw ValidationException.of("ddd", "DDD é obrigatório");
        }

        if (dto.numero() == null || dto.numero().isBlank()) {
            throw ValidationException.of("numero", "Número é obrigatório");
        }

        // cria o telefone
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        telefoneRepository.persist(telefone);

        return telefone;
    }

    // alterando um telefone
    @Override
    @Transactional
    public void update(Long id, TelefoneDTO dto) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Telefone telefone = telefoneRepository.findById(id);
        if (telefone == null) {
            throw ValidationException.of("id", "Telefone não encontrado");
        }

        if (dto == null) {
            throw ValidationException.of("dto", "Dados do telefone são obrigatórios");
        }

        if (dto.ddd() == null || dto.ddd().isBlank()) {
            throw ValidationException.of("ddd", "DDD é obrigatório");
        }

        if (dto.numero() == null || dto.numero().isBlank()) {
            throw ValidationException.of("numero", "Número é obrigatório");
        }

        // alterando os campos
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

    }

    // deletando um telefone
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("id", "id inválido");
        }

        Telefone telefone = telefoneRepository.findById(id);
        if (telefone == null) {
            throw ValidationException.of("id", "Telefone não encontrado");
        }

        telefoneRepository.delete(telefone);
    }

}