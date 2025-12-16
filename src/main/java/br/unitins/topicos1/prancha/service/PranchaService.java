package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.PranchaDTO;
import br.unitins.topicos1.prancha.model.Prancha;
import br.unitins.topicos1.prancha.model.TipoPrancha;
import jakarta.validation.Valid;

public interface PranchaService {
    
    List<Prancha> findAll();
    List<Prancha> findByTipoPrancha(TipoPrancha tipoPrancha);
    Prancha findById(Long id);
    Prancha create(@Valid PranchaDTO dto);
    void update(Long id, @Valid PranchaDTO dto);
    void delete(Long id);

}