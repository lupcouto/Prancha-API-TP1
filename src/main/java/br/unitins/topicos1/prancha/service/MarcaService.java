package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.MarcaDTO;
import br.unitins.topicos1.prancha.dto.PageResponse;
import br.unitins.topicos1.prancha.model.Marca;
import jakarta.validation.Valid;

public interface MarcaService {
    PageResponse<Marca> findAll(int page, int pageSize, String nome);
    List<Marca> findByNome(String nome);
    Marca findById(Long id);
    Marca create(@Valid MarcaDTO dto);
    void update(Long id, @Valid MarcaDTO dto);
    void delete(Long id);
}