package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.ModeloDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Marca;
import br.unitins.topicos1.prancha.model.Modelo;
import br.unitins.topicos1.prancha.repository.MarcaRepository;
import br.unitins.topicos1.prancha.repository.ModeloRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ModeloServiceImpl implements ModeloService {

    @Inject
    ModeloRepository modeloRepository;

    @Inject
    MarcaRepository marcaRepository;

    private static final Logger LOG = Logger.getLogger(ModeloServiceImpl.class);

    // busca todos os registros no banco
    @Override
    public List<Modelo> findAll() {
        LOG.info("Buscando todos os modelos cadastrados");

        List<Modelo> listaModelos = modeloRepository.listAll();

        if (listaModelos.isEmpty()) {
            LOG.warn("Nenhum modelo encontrado");
            throw ValidationException.of("Lista de Modelos", "Nenhum modelo cadastrado");
        }

        LOG.info("Total de modelos encontrados: " + listaModelos.size());
        return listaModelos;
    }

    // busca todos os registros pelo nome no banco
    @Override
    public List<Modelo> findByNome(String nome) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando modelos pelo nome: %s", nome);

        if (nome == null || nome.isBlank()) {
            LOG.error("Nome informado é inválido");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        List<Modelo> listaModelos = modeloRepository.findByNome(nome);

        if (listaModelos.isEmpty()) {
            LOG.warn("Nenhum modelo encontrado para o nome informado");
            throw ValidationException.of("nome", "Nenhum modelo encontrado para o nome informado");
        }

        LOG.info("Quantidade de modelos encontrados: " + listaModelos.size());
        return listaModelos;
    }

    // busca todos os registros pelo id no banco
    @Override
    public Modelo findById(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando modelo por ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido informado");
            throw ValidationException.of("id", "id inválido");
        }

        Modelo modelo = modeloRepository.findById(id);

        if (modelo == null) {
            LOG.warnf("Modelo não encontrado para o ID: %d", id);
            throw ValidationException.of("id", "Modelo não encontrado");
        }

        LOG.infof("Modelo encontrado: %s", modelo.getNome());
        return modelo;
    }

    // criando um modelo
    @Override
    @Transactional
    public Modelo create(ModeloDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Criando novo modelo");

        if (dto == null || dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome do modelo é obrigatório");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        if (dto.idMarca() == null || dto.idMarca() <= 0) {
            LOG.error("ID da marca inválido");
            throw ValidationException.of("idMarca", "id da marca inválido");
        }

        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) {
            LOG.warn("Marca não encontrada para o ID informado");
            throw ValidationException.of("idMarca", "Marca não encontrada");
        }

        // cria o modelo
        Modelo modelo = new Modelo();
        modelo.setNome(dto.nome());
        modelo.setMarca(marca); // víncula com a marca

        modeloRepository.persist(modelo);

        LOG.infof("Modelo criado com sucesso: %s (Marca: %s)", modelo.getNome(), marca.getNome());

        return modelo;
    }

    // alterando um modelo
    @Override
    @Transactional
    public void update(Long id, ModeloDTO dto) {

        // validações nos campos obrigatórios
        LOG.infof("Atualizando modelo ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID do modelo é inválido");
            throw ValidationException.of("id", "id inválido");
        }

        Modelo modelo = modeloRepository.findById(id);
        if (modelo == null) {
            LOG.warn("Modelo não encontrado para atualização");
            throw ValidationException.of("id", "Modelo não encontrado");
        }

        if (dto == null || dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome do modelo é obrigatório");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        if (dto.idMarca() == null || dto.idMarca() <= 0) {
            LOG.error("ID da marca é inválido");
            throw ValidationException.of("idMarca", "id da marca inválido");
        }

        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) {
            LOG.warn("Marca não encontrada para atualização do modelo");
            throw ValidationException.of("idMarca", "Marca não encontrada");
        }

        // altera o modelo e a marca
        modelo.setNome(dto.nome());
        modelo.setMarca(marca);

        LOG.infof("Modelo atualizado com sucesso: %s", modelo.getNome());
    }

    // deletando um modelo
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Excluindo modelo ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido informado");
            throw ValidationException.of("id", "id inválido");
        }

        Modelo modelo = modeloRepository.findById(id);
        if (modelo == null) {
            LOG.warn("Modelo não encontrado para exclusão");
            throw ValidationException.of("id", "Modelo não encontrado");
        }

        modeloRepository.delete(modelo);

        LOG.info("Modelo removido com sucesso");
    }
}