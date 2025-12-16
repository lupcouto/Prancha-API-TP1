package br.unitins.topicos1.prancha.service;
import java.util.List;
import org.jboss.logging.Logger;
import br.unitins.topicos1.prancha.dto.PranchaDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Fornecedor;
import br.unitins.topicos1.prancha.model.Modelo;
import br.unitins.topicos1.prancha.model.Prancha;
import br.unitins.topicos1.prancha.model.Quilha;
import br.unitins.topicos1.prancha.model.TipoPrancha;
import br.unitins.topicos1.prancha.repository.FornecedorRepository;
import br.unitins.topicos1.prancha.repository.ModeloRepository;
import br.unitins.topicos1.prancha.repository.PranchaRepository;
import br.unitins.topicos1.prancha.repository.QuilhaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PranchaServiceImpl implements PranchaService {

    private static final Logger LOG = Logger.getLogger(PranchaServiceImpl.class);

    @Inject
    PranchaRepository repository;

    @Inject
    ModeloRepository modeloRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    QuilhaRepository quilhaRepository;

    // busca todos os registros no banco
    @Override
    public List<Prancha> findAll() {
        LOG.info("Buscando todas as pranchas...");

        List<Prancha> listaPranchas = repository.listAll();

        if (listaPranchas.isEmpty()) {
            LOG.warn("Nenhuma prancha cadastrada.");
            throw ValidationException.of("Lista de Pranchas", "Nenhuma prancha cadastrada");
        }

        LOG.info("Total de pranchas encontradas: " + listaPranchas.size());
        return listaPranchas;
    }

    // busca todos os registros pelo tipo de prancha no banco
    @Override
    public List<Prancha> findByTipoPrancha(TipoPrancha tipoPrancha) {

        // validações nos campos obrigatórios
        LOG.info("Buscando pranchas por tipo: " + tipoPrancha);

        if (tipoPrancha == null) {
            LOG.error("TipoPrancha não informado.");
            throw ValidationException.of("tipoPrancha", "TipoPrancha é obrigatório");
        }

        List<Prancha> listaPranchas = repository.findByTipoPrancha(tipoPrancha);
        if (listaPranchas.isEmpty()) {
            LOG.warn("Nenhuma prancha encontrada do tipo: " + tipoPrancha);
            throw ValidationException.of("tipoPrancha", "Nenhuma prancha encontrada para o tipo informado");
        }

        LOG.info("Total de pranchas encontradas: " + listaPranchas.size());
        return listaPranchas;
    }

    // busca todos os registros pelo id no banco
    @Override
    public Prancha findById(Long id) {

        // validações nos campos obrigatórios
        LOG.info("Buscando prancha por ID: " + id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido.");
            throw ValidationException.of("id", "id inválido");
        }

        Prancha prancha = repository.findById(id);
        if (prancha == null) {
            LOG.warn("Prancha não encontrada para ID: " + id);
            throw ValidationException.of("id", "Prancha não encontrada");
        }

        LOG.info("Prancha encontrada: ID = " + prancha.getId());
        return prancha;
    }

    // criando uma prancha
    @Override
    @Transactional
    public Prancha create(PranchaDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Criando nova prancha...");

        if (dto == null) {
            LOG.error("DTO da prancha não informado.");
            throw ValidationException.of("dto", "Dados da prancha são obrigatórios");
        }

        Modelo modelo = modeloRepository.findById(dto.idModelo());
        if (modelo == null) {
            LOG.error("Modelo não encontrado. ID = " + dto.idModelo());
            throw ValidationException.of("idModelo", "Modelo não encontrado");
        }

        Quilha quilha = quilhaRepository.findById(dto.idQuilha());
        if (quilha == null) {
            LOG.error("Quilha não encontrada. ID = " + dto.idQuilha());
            throw ValidationException.of("idQuilha", "Quilha não encontrada");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor());
        if (fornecedor == null) {
            LOG.error("Fornecedor não encontrado. ID = " + dto.idFornecedor());
            throw ValidationException.of("idFornecedor", "Fornecedor não encontrado");
        }

        // cria a prancha
        Prancha prancha = new Prancha();
        prancha.setTamanho(dto.tamanho());
        prancha.setValor(dto.valor());
        prancha.setEstoque(dto.estoque());
        prancha.setTipoPrancha(dto.tipoPrancha());
        prancha.setHabilidade(dto.habilidade());
        prancha.setModelo(modelo); // associa ao modelo
        prancha.setQuilhas(List.of(quilha)); // associa a UMA quilha
        prancha.setFornecedores(List.of(fornecedor)); // associa a UM fornecedor

        repository.persist(prancha);

        LOG.info("Prancha criada com sucesso! ID = " + prancha.getId());
        return prancha;
    }

    // alterando uma prancha
    @Override
    @Transactional
    public void update(Long id, PranchaDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Atualizando prancha ID: " + id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido para atualização.");
            throw ValidationException.of("id", "id inválido");
        }
        if (dto == null) {
            LOG.error("DTO não informado na atualização.");
            throw ValidationException.of("dto", "Dados da prancha são obrigatórios");
        }

        Prancha prancha = repository.findById(id);
        if (prancha == null) {
            LOG.warn("Prancha não encontrada para atualização. ID: " + id);
            throw ValidationException.of("id", "Prancha não encontrada");
        }

        Modelo modelo = modeloRepository.findById(dto.idModelo());
        if (modelo == null) {
            LOG.error("Modelo não encontrado na atualização. ID = " + dto.idModelo());
            throw ValidationException.of("idModelo", "Modelo não encontrado");
        }

        Quilha quilha = quilhaRepository.findById(dto.idQuilha());
        if (quilha == null) {
            LOG.error("Quilha não encontrada na atualização. ID = " + dto.idQuilha());
            throw ValidationException.of("idQuilha", "Quilha não encontrada");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor());
        if (fornecedor == null) {
            LOG.error("Fornecedor não encontrado na atualização. ID = " + dto.idFornecedor());
            throw ValidationException.of("idFornecedor", "Fornecedor não encontrado");
        }

        // alterando os campos
        prancha.setTamanho(dto.tamanho());
        prancha.setValor(dto.valor());
        prancha.setEstoque(dto.estoque());
        prancha.setTipoPrancha(dto.tipoPrancha());
        prancha.setHabilidade(dto.habilidade());
        prancha.setModelo(modelo);
        prancha.setQuilhas(List.of(quilha)); 
        prancha.setFornecedores(List.of(fornecedor));

        LOG.info("Prancha atualizada com sucesso. ID = " + id);
    }

    // deletando uma prancha
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        LOG.info("Deletando prancha ID: " + id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido na exclusão.");
            throw ValidationException.of("id", "id inválido");
        }

        Prancha prancha = repository.findById(id);
        if (prancha == null) {
            LOG.warn("Tentativa de deletar prancha inexistente. ID: " + id);
            throw ValidationException.of("id", "Prancha não encontrada");
        }

        repository.delete(prancha);

        LOG.info("Prancha deletada com sucesso. ID = " + id);
    }
}