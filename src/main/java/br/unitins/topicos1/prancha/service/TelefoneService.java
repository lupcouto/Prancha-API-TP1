package br.unitins.topicos1.prancha.service;
import br.unitins.topicos1.prancha.dto.TelefoneDTO;
import br.unitins.topicos1.prancha.model.Telefone;
import jakarta.validation.Valid;

import java.util.List;

public interface TelefoneService {
    
    List<Telefone> findAll(); 
    List<Telefone> findByNumero(String numero);
    Telefone findById(Long id);
    Telefone create(@Valid TelefoneDTO dto);
    void update(Long id, @Valid TelefoneDTO dto);
    void delete(Long id);

}