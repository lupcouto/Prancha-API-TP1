package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.EnderecoDTO;
import br.unitins.topicos1.prancha.model.Endereco;
import jakarta.validation.Valid;

public interface EnderecoService {

    List<Endereco> findAll();
    List<Endereco> findByCep(String cep);
    Endereco findById(Long id);
    Endereco create(@Valid EnderecoDTO dto);
    void update(Long id, @Valid EnderecoDTO dto);
    void delete(Long id);
    
}