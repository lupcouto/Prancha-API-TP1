package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.FornecedorDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Fornecedor;
import br.unitins.topicos1.prancha.model.Telefone;
import br.unitins.topicos1.prancha.repository.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {

    @Inject
    FornecedorRepository fornecedorRepository;

    private static final Logger LOG = Logger.getLogger(FornecedorServiceImpl.class);

    // busca todos os registros no banco
    @Override
    public List<Fornecedor> findAll() {
        LOG.info("Buscando todos os fornecedores cadastrados");

        List<Fornecedor> listaFornecedor = fornecedorRepository.listAll();

        if (listaFornecedor.isEmpty()) {
            LOG.warn("Nenhum fornecedor encontrado");
            throw ValidationException.of("Lista de Fornecedores", "Nenhum fornecedor cadastrado");
        }

        LOG.info("Total de fornecedores encontrados: " + listaFornecedor.size());
        return listaFornecedor;
    }

    // busca todos os registros pelo cnpj no banco
    @Override
    public List<Fornecedor> findByCnpj(String cnpj) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando fornecedores pelo CNPJ: %s", cnpj);

        if (cnpj == null || cnpj.isBlank()) {
            LOG.error("CNPJ informado é inválido");
            throw ValidationException.of("cnpj", "CNPJ é obrigatório");
        }

        List<Fornecedor> listaFornecedor = fornecedorRepository.findByCnpj(cnpj);

        if (listaFornecedor.isEmpty()) {
            LOG.warn("Nenhum fornecedor encontrado para o CNPJ informado");
            throw ValidationException.of("cnpj", "Nenhum fornecedor encontrado para o CNPJ informado");
        }

        LOG.info("Fornecedores encontrados: " + listaFornecedor.size());
        return listaFornecedor;
    }

    // busca todos os registros pelo id no banco
    @Override
    public Fornecedor findById(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Buscando fornecedor por ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID informado é inválido");
            throw ValidationException.of("id", "id inválido");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(id);

        if (fornecedor == null) {
            LOG.warnf("Fornecedor não encontrado para o ID: %d", id);
            throw ValidationException.of("id", "Fornecedor não encontrado");
        }

        LOG.infof("Fornecedor encontrado: %s", fornecedor.getNome());
        return fornecedor;
    }

    // criando um fornecedor
    @Override
    @Transactional
    public Fornecedor create(FornecedorDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Criando novo fornecedor");

        if (dto == null) {
            LOG.error("DTO do fornecedor não informado");
            throw ValidationException.of("dto", "Dados do fornecedor não informados");
        }

        if (dto.cnpj() == null || dto.cnpj().isBlank()) {
            LOG.error("CNPJ é obrigatório");
            throw ValidationException.of("cnpj", "CNPJ é obrigatório");
        }

        if (dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome do fornecedor é obrigatório");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        if (dto.ddd() == null || dto.ddd().isBlank()) {
            LOG.error("DDD do telefone é obrigatório");
            throw ValidationException.of("DDD", "DDD é obrigatório");
        }

        if (dto.numero() == null || dto.numero().isBlank()) {
            LOG.error("Número do telefone é obrigatório");
            throw ValidationException.of("Número", "Número é obrigatório");
        }

        // cria o telefone
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        // cria o fornecedor
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setTelefone(telefone);
        fornecedor.setCnpj(dto.cnpj());

        fornecedorRepository.persist(fornecedor);

        LOG.infof("Fornecedor criado com sucesso: %s (CNPJ: %s)", fornecedor.getNome(), fornecedor.getCnpj());

        return fornecedor;
    }

    // alterando um fornecedor
    @Override
    @Transactional
    public void update(Long id, FornecedorDTO dto) {

        // validações nos campos obrigatórios
        LOG.infof("Atualizando fornecedor ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID informado é inválido");
            throw ValidationException.of("id", "id inválido");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            LOG.warn("Fornecedor não encontrado para atualização");
            throw ValidationException.of("id", "Fornecedor não encontrado");
        }

        if (dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome é obrigatório");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        if (dto.ddd() == null || dto.ddd().isBlank()) {
            LOG.error("DDD é obrigatório");
            throw ValidationException.of("DDD", "DDD é obrigatório");
        }

        if (dto.numero() == null || dto.numero().isBlank()) {
            LOG.error("Número é obrigatório");
            throw ValidationException.of("Número", "Número é obrigatório");
        }

        if (dto.cnpj() == null || dto.cnpj().isBlank()) {
            LOG.error("CNPJ é obrigatório");
            throw ValidationException.of("cnpj", "CNPJ é obrigatório");
        }

        // alterando os campos de fornecedor
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());

        // alterando o telefone. Se o fornecedor ainda não tiver um, ele cria um novo
        Telefone telefone = fornecedor.getTelefone();
        if (telefone == null) {
            telefone = new Telefone();
            fornecedor.setTelefone(telefone);
        }
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        LOG.infof("Fornecedor atualizado com sucesso: %s", fornecedor.getNome());
    }

    // deletando um fornecedor
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        LOG.infof("Excluindo fornecedor ID: %d", id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido informado para exclusão");
            throw ValidationException.of("id", "id inválido");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            LOG.warn("Fornecedor não encontrado para exclusão");
            throw ValidationException.of("id", "Fornecedor não encontrado");
        }

        // deleta o fornecedor encontrado
        fornecedorRepository.delete(fornecedor);

        LOG.info("Fornecedor removido com sucesso");
    }
}