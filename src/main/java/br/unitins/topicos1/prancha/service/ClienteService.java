package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.ClienteDTO;
import br.unitins.topicos1.prancha.model.Cliente;
import jakarta.validation.Valid;

public interface ClienteService {

    List<Cliente> findAll();
    List<Cliente> findByCpf(String cpf);
    Cliente findById(Long id);
    Cliente create(@Valid ClienteDTO dto);
    void update(Long id, @Valid ClienteDTO dto);
    void delete(Long id);
    
}