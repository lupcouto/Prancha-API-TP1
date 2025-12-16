package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.FornecedorDTO;
import br.unitins.topicos1.prancha.model.Fornecedor;
import jakarta.validation.Valid;

public interface FornecedorService {

    List<Fornecedor> findAll();
    List<Fornecedor> findByCnpj(String cnpj);
    Fornecedor findById(Long id);
    Fornecedor create(@Valid FornecedorDTO dto);
    void update(Long id, @Valid FornecedorDTO dto);
    void delete(Long id);
    
}