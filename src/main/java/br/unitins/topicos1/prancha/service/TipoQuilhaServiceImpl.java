package br.unitins.topicos1.prancha.service;
import java.util.List;
import org.jboss.logging.Logger;
import br.unitins.topicos1.prancha.dto.TipoQuilhaDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import br.unitins.topicos1.prancha.repository.TipoQuilhaRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoQuilhaServiceImpl implements TipoQuilhaService {

    private static final Logger LOG = Logger.getLogger(TipoQuilhaServiceImpl.class);

    @Inject
    TipoQuilhaRepository tipoQuilhaRepository;

    // busca todos os registros no banco
    @Override
    public List<TipoQuilha> findAll() {
        LOG.info("Buscando todos os tipos de quilha...");
        List<TipoQuilha> listaTipoQuilhas = tipoQuilhaRepository.listAll();

        if (listaTipoQuilhas.isEmpty()) {
            LOG.warn("Nenhum tipo de quilha encontrado.");
            throw ValidationException.of("lista", "Nenhum tipo de quilha cadastrado");
        }

        return listaTipoQuilhas;
    }

    // busca todos os registros pelo nome no banco
    @Override
    public List<TipoQuilha> findByNome(String nome) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando tipo de quilha pelo nome: %s", nome);

        if (nome == null || nome.isBlank()) {
            LOG.error("Nome informado é inválido.");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        List<TipoQuilha> listaTipoQuilhas = tipoQuilhaRepository.findByNome(nome);

        if (listaTipoQuilhas.isEmpty()) {
            LOG.warnf("Nenhum tipo de quilha encontrado com o nome %s", nome);
            throw ValidationException.of("nome", "Nenhum tipo de quilha encontrado com o nome informado");
        }

        return listaTipoQuilhas;
    }

    // busca todos os registros pelo id no banco
    @Override
    public TipoQuilha findById(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando tipo de quilha por ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido ao buscar tipo de quilha.");
            throw ValidationException.of("id", "id inválido");
        }

        TipoQuilha tipoQuilha = tipoQuilhaRepository.findById(id);
        if (tipoQuilha == null) {
            LOG.warnf("Tipo de quilha com id %d não encontrado.", id);
            throw ValidationException.of("id", "Tipo Quilha não encontrado");
        }

        return tipoQuilha;
    }

    // cria um tipo de quilha
    @Override
    @Transactional
    public TipoQuilha create(TipoQuilhaDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Criando novo tipo de quilha...");

        if (dto == null) {
            LOG.error("DTO nulo na criação do tipo de quilha.");
            throw ValidationException.of("dto", "Dados do tipo quilha são obrigatórios");
        }

        if (dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome inválido para tipo de quilha.");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        // cria o tipo
        TipoQuilha tipoQuilha = new TipoQuilha();
        tipoQuilha.setNome(dto.nome());

        tipoQuilhaRepository.persist(tipoQuilha);

        LOG.info("Tipo de quilha criado com sucesso.");
        return tipoQuilha;
    }

    // alterando um tipo de quilha
    @Override
    @Transactional
    public void update(Long id, TipoQuilhaDTO dto) {

        // validações nos campos obrigatórios
        LOG.infof("Atualizando tipo de quilha ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido na atualização.");
            throw ValidationException.of("id", "id inválido");
        }

        TipoQuilha tipoQuilha = tipoQuilhaRepository.findById(id);
        if (tipoQuilha == null) {
            LOG.warnf("Tipo de quilha ID %d não encontrado para atualização.", id);
            throw ValidationException.of("id", "Tipo Quilha não encontrado");
        }

        if (dto == null) {
            LOG.error("DTO nulo na atualização.");
            throw ValidationException.of("dto", "Dados do tipo quilha são obrigatórios");
        }

        if (dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome inválido na atualização.");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        // alterando o campo
        tipoQuilha.setNome(dto.nome());

        LOG.info("Tipo de quilha atualizado com sucesso.");
    }

    // deletando um tipo de quilha
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Deletando tipo de quilha ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido na deleção.");
            throw ValidationException.of("id", "id inválido");
        }

        TipoQuilha tipoQuilha = tipoQuilhaRepository.findById(id);
        if (tipoQuilha == null) {
            LOG.warnf("Tipo de quilha ID %d não encontrado para deletar.", id);
            throw ValidationException.of("id", "Tipo Quilha não encontrado");
        }

        tipoQuilhaRepository.delete(tipoQuilha);

        LOG.info("Tipo de quilha deletado com sucesso.");
    }
}