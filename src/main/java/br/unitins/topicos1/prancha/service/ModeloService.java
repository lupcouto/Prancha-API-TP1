package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.ModeloDTO;
import br.unitins.topicos1.prancha.model.Modelo;
import jakarta.validation.Valid;

public interface ModeloService {

    List<Modelo> findAll();
    List<Modelo> findByNome(String nome);
    Modelo findById(Long id);
    Modelo create(@Valid ModeloDTO dto);
    void update(Long id, @Valid ModeloDTO dto);
    void delete(Long id);
    
}