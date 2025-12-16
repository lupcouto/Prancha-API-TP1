package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.TipoQuilhaDTO;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import jakarta.validation.Valid;

public interface TipoQuilhaService {

    List<TipoQuilha> findAll();
    List<TipoQuilha> findByNome(String nome);
    TipoQuilha findById(Long id);
    TipoQuilha create(@Valid TipoQuilhaDTO dto);
    void update(Long id, @Valid TipoQuilhaDTO dto);
    void delete(Long id);
    
}