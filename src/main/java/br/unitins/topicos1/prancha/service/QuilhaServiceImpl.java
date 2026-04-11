package br.unitins.topicos1.prancha.service;

import java.util.List;
import org.jboss.logging.Logger;

import br.unitins.topicos1.prancha.dto.PageResponse;
import br.unitins.topicos1.prancha.dto.QuilhaDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Quilha;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import br.unitins.topicos1.prancha.repository.QuilhaRepository;
import br.unitins.topicos1.prancha.repository.TipoQuilhaRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuilhaServiceImpl implements QuilhaService {

    private static final Logger LOG = Logger.getLogger(QuilhaServiceImpl.class);

    @Inject
    QuilhaRepository quilhaRepository;

    @Inject
    TipoQuilhaRepository tipoQuilhaRepository;

    // busca todos os registros no banco
    @Override
    public PageResponse<Quilha> findAll(int page, int pageSize) {

        LOG.info("Buscando quilhas paginadas...");

        if (pageSize > 100) {
            pageSize = 100;
        }

        var query = quilhaRepository.findAll();
        query.page(page, pageSize);

        PageResponse<Quilha> response = new PageResponse<>();
        response.content = query.list();
        response.totalRegistros = query.count();
        response.totalPaginas = query.pageCount();
        response.pagina = page;
        response.pageSize = pageSize;

        return response;
    }

    // busca todos os registros pelo tipo de quilha no banco
    @Override
    public List<Quilha> findByTipoQuilha(TipoQuilha tipoQuilha) {

        // validações nos campos obrigatórios
        LOG.info("Buscando quilhas por tipo...");

        if (tipoQuilha == null) {
            LOG.error("Tipo de quilha nulo informado.");
            throw ValidationException.of("tipoQuilha", "Tipo quilha é obrigatório");
        }

        List<Quilha> listaQuilhas = quilhaRepository.findByTipoQuilha(tipoQuilha);

        if (listaQuilhas.isEmpty()) {
            LOG.warnf("Nenhuma quilha encontrada para o tipo informado: %s", tipoQuilha.getNome());
            throw ValidationException.of("tipoQuilha", "Nenhuma quilha encontrada para o tipo informado");
        }

        LOG.info("Quantidade de quilhas encontradas para o tipo: " + listaQuilhas.size());
        return listaQuilhas;
    }

    // busca todos os registros pelo id no banco
    @Override
    public Quilha findById(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando quilha por ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido informado.");
            throw ValidationException.of("id", "id inválido");
        }

        Quilha quilha = quilhaRepository.findById(id);

        if (quilha == null) {
            LOG.warnf("Quilha não encontrada para o ID: %d", id);
            throw ValidationException.of("id", "Quilha não encontrada");
        }

        return quilha;
    }

    // criando uma quilha
    @Override
    @Transactional
    public Quilha create(QuilhaDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Criando nova quilha...");

        if (dto == null || dto.descricaoQuilha() == null || dto.descricaoQuilha().isBlank()) {
            LOG.error("Descrição de quilha inválida.");
            throw ValidationException.of("descricaoQuilha", "Descrição da quilha é obrigatória");
        }

        if (dto.idTipoQuilha() == null || dto.idTipoQuilha() <= 0) {
            LOG.error("ID do tipo de quilha inválido.");
            throw ValidationException.of("idTipoQuilha", "id do tipo de quilha inválido");
        }

        TipoQuilha tipoQuilha = tipoQuilhaRepository.findById(dto.idTipoQuilha());

        if (tipoQuilha == null) {
            LOG.errorf("Tipo de quilha não encontrado. ID: %d", dto.idTipoQuilha());
            throw ValidationException.of("idTipoQuilha", "Tipo quilha não encontrada");
        }

        // cria a quilha
        Quilha quilha = new Quilha();
        quilha.setDescricaoQuilha(dto.descricaoQuilha());
        quilha.setTipoQuilha(tipoQuilha); // associa ao tipo de quilha

        quilhaRepository.persist(quilha);

        LOG.info("Quilha criada com sucesso.");
        return quilha;
    }

    // alterando uma quilha
    @Override
    @Transactional
    public void update(Long id, QuilhaDTO dto) {

        // validações nos campos obrigatórios
        LOG.infof("Atualizando quilha ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido ao atualizar quilha.");
            throw ValidationException.of("id", "id inválido");
        }

        Quilha quilha = quilhaRepository.findById(id);

        if (quilha == null) {
            LOG.warnf("Quilha não encontrada para atualização. ID: %d", id);
            throw ValidationException.of("id", "Quilha não encontrada");
        }

        if (dto == null || dto.descricaoQuilha() == null || dto.descricaoQuilha().isBlank()) {
            LOG.error("Descrição da quilha inválida na atualização.");
            throw ValidationException.of("descricaoQuilha", "Descrição da quilha é obrigatória");
        }

        if (dto.idTipoQuilha() == null || dto.idTipoQuilha() <= 0) {
            LOG.error("ID de tipo de quilha inválido na atualização.");
            throw ValidationException.of("idTipoQuilha", "id do tipo de quilha inválido");
        }

        TipoQuilha tipoQuilha = tipoQuilhaRepository.findById(dto.idTipoQuilha());

        if (tipoQuilha == null) {
            LOG.errorf("Tipo de quilha não encontrado na atualização. ID: %d", dto.idTipoQuilha());
            throw ValidationException.of("idTipoQuilha", "Tipo de quilha não encontrada");
        }

        // alterando os campos
        quilha.setDescricaoQuilha(dto.descricaoQuilha());
        quilha.setTipoQuilha(tipoQuilha);

        LOG.info("Quilha atualizada com sucesso.");
    }

    // deletando uma quilha
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Deletando quilha ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido ao deletar.");
            throw ValidationException.of("id", "id inválido");
        }

        Quilha quilha = quilhaRepository.findById(id);

        if (quilha == null) {
            LOG.warnf("Quilha não encontrada para deleção. ID: %d", id);
            throw ValidationException.of("id", "Quilha não encontrada");
        }

        quilhaRepository.delete(quilha);

        LOG.info("Quilha deletada com sucesso.");
    }
}