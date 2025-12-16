package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.PixDTO;
import br.unitins.topicos1.prancha.dto.PixResponseDTO;
import jakarta.validation.Valid;

public interface PixService {

    List<PixResponseDTO> findAll();
    List<PixResponseDTO> findByChave(String chave);
    PixResponseDTO findById(Long id);
    PixResponseDTO create(@Valid PixDTO dto);
    void update(Long id, @Valid PixDTO dto);
    void delete(Long id);
    
}
