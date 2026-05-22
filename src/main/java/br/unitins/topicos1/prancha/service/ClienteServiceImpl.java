package br.unitins.topicos1.prancha.service;

import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;
import br.unitins.topicos1.prancha.dto.ClienteDTO;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Cliente;
import br.unitins.topicos1.prancha.model.Endereco;
import br.unitins.topicos1.prancha.model.Telefone;
import br.unitins.topicos1.prancha.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    private static final Logger LOG = Logger.getLogger(ClienteServiceImpl.class);

    @Inject
    ClienteRepository clienteRepository;

    // buscando todos os registros no banco
    @Override
    public List<Cliente> findAll() {
        LOG.info("Buscando todos os clientes...");

        List<Cliente> listaCliente = clienteRepository.listAll();
        if (listaCliente.isEmpty()) {
            LOG.warn("Nenhum cliente encontrado.");
            throw ValidationException.of("Lista de Clientes", "Nenhum cliente cadastrado");
        }

        LOG.info("Total de clientes encontrados: " + listaCliente.size());
        return listaCliente;
    }

    // buscando todos os registros no banco pelo cpf
    @Override
    public List<Cliente> findByCpf(String cpf) {

        // validações nos campos obrigatórios
        LOG.info("Buscando clientes pelo CPF: " + cpf);

        if (cpf == null || cpf.isBlank()) {
            LOG.error("CPF não informado.");
            throw ValidationException.of("cpf", "CPF é obrigatório");
        }

        List<Cliente> listaCliente = clienteRepository.findByCpf(cpf);
        if (listaCliente.isEmpty()) {
            LOG.warn("Nenhum cliente encontrado para o CPF: " + cpf);
            throw ValidationException.of("cpf", "Nenhum cliente encontrado para o CPF informado");
        }

        LOG.info("Clientes encontrados: " + listaCliente.size());
        return listaCliente;
    }

    // buscando todos os registros no banco pelo id
    @Override
    public Cliente findById(Long id) {

        // validações nos campos obrigatórios
        LOG.info("Buscando cliente pelo ID: " + id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido: " + id);
            throw ValidationException.of("id", "id inválido");
        }

        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            LOG.warn("Cliente não encontrado para o ID: " + id);
            throw ValidationException.of("id", "Cliente não encontrado");
        }

        LOG.info("Cliente encontrado: " + cliente.getNome());
        return cliente;
    }

    // criando um cliente
    @Override
    @Transactional
    public Cliente create(@Valid ClienteDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Criando novo cliente...");

        if (dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome não informado.");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        if (dto.ddd() == null || dto.ddd().isBlank()) {
            LOG.error("DDD não informado.");
            throw ValidationException.of("DDD", "DDD é obrigatório");
        }

        if (dto.numero() == null || dto.numero().isBlank()) {
            LOG.error("Número de telefone não informado.");
            throw ValidationException.of("Número", "Número é obrigatório");
        }

        if (dto.cpf() == null || dto.cpf().isBlank()) {
            LOG.error("CPF não informado.");
            throw ValidationException.of("CPF", "CPF é obrigatório");
        }

        // cria o telefone
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        Endereco endereco = new Endereco();
        endereco.setCidade(dto.endereco().cidade());
        endereco.setEstado(dto.endereco().estado());
        endereco.setCep(dto.endereco().cep());
        endereco.setBairro(dto.endereco().bairro());
        endereco.setQuadra(dto.endereco().quadra());
        endereco.setAlameda(dto.endereco().alameda());
        endereco.setNumero(dto.endereco().numero());
        endereco.setComplemento(dto.endereco().complemento());

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setTelefone(telefone);
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        endereco.setCliente(cliente);
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        cliente.setEnderecos(enderecos);

        clienteRepository.persist(cliente);

        LOG.info("Cliente criado com sucesso. ID = " + cliente.getId());
        return cliente;
    }

    // alterando um cliente
    @Override
    @Transactional
    public void update(Long id, @Valid ClienteDTO dto) {

        // validações nos campos obrigatórios
        LOG.info("Atualizando cliente ID: " + id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido na atualização: " + id);
            throw ValidationException.of("id", "id inválido");
        }

        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            LOG.warn("Cliente não encontrado para atualização. ID: " + id);
            throw ValidationException.of("id", "Cliente não encontrado");
        }

        if (dto.nome() == null || dto.nome().isBlank()) {
            LOG.error("Nome não informado na atualização.");
            throw ValidationException.of("nome", "Nome é obrigatório");
        }

        if (dto.ddd() == null || dto.ddd().isBlank()) {
            LOG.error("DDD não informado na atualização.");
            throw ValidationException.of("DDD", "DDD é obrigatório");
        }

        if (dto.numero() == null || dto.numero().isBlank()) {
            LOG.error("Número não informado na atualização.");
            throw ValidationException.of("Número", "Número é obrigatório");
        }

        if (dto.cpf() == null || dto.cpf().isBlank()) {
            LOG.error("CPF não informado na atualização.");
            throw ValidationException.of("cpf", "CPF é obrigatório");
        }

        // alterando os campos do cliente
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        // alterando o telefone. Se o cliente ainda não tiver um, ele cria um novo
        Telefone telefone = cliente.getTelefone();
        if (telefone == null) {
            LOG.info("Criando telefone para cliente que não tinha telefone cadastrado.");
            telefone = new Telefone();
            cliente.setTelefone(telefone);
        }
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        LOG.info("Cliente atualizado com sucesso.");
    }

    // deletando um cliente
    @Override
    @Transactional
    public void delete(Long id) {

        // validações nos campos obrigatórios
        LOG.info("Deletando cliente ID: " + id);

        if (id == null || id <= 0) {
            LOG.error("ID inválido na exclusão: " + id);
            throw ValidationException.of("id", "id inválido");
        }

        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            LOG.warn("Tentativa de deletar cliente falha. ID: " + id);
            throw ValidationException.of("id", "Cliente não encontrado");
        }

        // deleta o cliente encontrado
        clienteRepository.delete(cliente);
        LOG.info("Cliente deletado com sucesso.");
    }
}