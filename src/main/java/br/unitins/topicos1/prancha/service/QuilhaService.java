package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.PageResponse;
import br.unitins.topicos1.prancha.dto.QuilhaDTO;
import br.unitins.topicos1.prancha.model.Quilha;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import jakarta.validation.Valid;

public interface QuilhaService {

    PageResponse<Quilha> findAll(int page, int pageSize);
    List<Quilha> findByTipoQuilha(TipoQuilha tipoQuilha);
    Quilha findById(Long id);
    Quilha create(@Valid QuilhaDTO dto);
    void update(Long id, @Valid QuilhaDTO dto);
    void delete(Long id);
    
}