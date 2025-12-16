package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.MarcaDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Marca;
import br.unitins.topicos1.prancha.repository.MarcaRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository marcaRepository;

    private static final Logger LOG = Logger.getLogger(MarcaServiceImpl.class);

    // busca todos os registros no banco
    @Override
    public List<Marca> findAll() {
        LOG.info("Buscando todas as marcas");

        List<Marca> listaMarcas = marcaRepository.listAll();

        if (listaMarcas.isEmpty()) {
            LOG.warn("Nenhuma marca encontrada");
            throw ValidationException.of("Lista de Marcas", "Nenhuma marca cadastrada");
        }

        LOG.info("Total de marcas encontradas: " + listaMarcas.size());
        return listaMarcas;
    }

    // busca todos os registros pelo nome no banco
    @Override
    public List<Marca> findByNome(String nome) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando marca pelo nome: %s", nome);

        if (nome == null || nome.isBlank()) {
            LOG.error("Nome não informado");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        List<Marca> listaMarcas = marcaRepository.findByNome(nome);

        if (listaMarcas.isEmpty()) {
            LOG.warn("Nenhuma marca encontrada com o nome informado");
            throw ValidationException.of("nome", "Nenhuma marca encontrada para o nome informado");
        }

        LOG.info("Marcas encontradas: " + listaMarcas.size());
        return listaMarcas;
    }

    // busca todos os registros pelo id no banco
    @Override
    public Marca findById(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando marca por ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido");
            throw ValidationException.of("id", "id inválido");
        }

        Marca marca = marcaRepository.findById(id);

        if (marca == null) {
            LOG.warnf("Marca não encontrada com ID: %d", id);
            throw ValidationException.of("id", "Marca não encontrada");
        }

        LOG.infof("Marca encontrada: %s", marca.getNome());
        return marca;
    }

    // criando uma marca
    @Override
    @Transactional
    public Marca create(MarcaDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Criando nova marca");

        if (dto == null || dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome da marca é obrigatório");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        if (dto.paisOrigem() == null || dto.paisOrigem().isBlank()) {
            LOG.error("País de origem é obrigatório");
            throw ValidationException.of("paisOrigem", "País de origem é obrigatório");
        }

        // cria a marca
        Marca marca = new Marca();
        marca.setNome(dto.nome());
        marca.setPaisOrigem(dto.paisOrigem());

        marcaRepository.persist(marca);
        LOG.infof("Marca criada com sucesso: %s", marca.getNome());

        return marca;
    }

    // alterando uma marca
    @Override
    @Transactional
    public void update(Long id, MarcaDTO dto) {

        // validações nos campos obrigatórios
        LOG.infof("Atualizando marca ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido");
            throw ValidationException.of("id", "id inválido");
        }

        Marca marca = marcaRepository.findById(id);
        if (marca == null) {
            LOG.warn("Marca não encontrada");
            throw ValidationException.of("id", "Marca não encontrada");
        }

        if (dto == null || dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome é obrigatório");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }
        if (dto.paisOrigem() == null || dto.paisOrigem().isBlank()) {
            LOG.error("País de origem é obrigatório");
            throw ValidationException.of("paisOrigem", "País de origem é obrigatório");
        }

        // alterando os campos
        marca.setNome(dto.nome());
        marca.setPaisOrigem(dto.paisOrigem());

        LOG.info("Marca atualizada com sucesso");
    }

    // deletando uma marca
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Excluindo marca ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido");
            throw ValidationException.of("id", "id inválido");
        }

        Marca marca = marcaRepository.findById(id);
        if (marca == null) {
            LOG.warn("Marca não encontrada para exclusão");
            throw ValidationException.of("id", "Marca não encontrada");
        }

        // deleta a marca encontrada
        marcaRepository.delete(marca);
        LOG.info("Marca removida com sucesso");
    }
}