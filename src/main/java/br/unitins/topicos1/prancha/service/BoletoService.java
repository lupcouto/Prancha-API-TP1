package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.BoletoDTO;
import br.unitins.topicos1.prancha.dto.BoletoResponseDTO;
import jakarta.validation.Valid;

public interface BoletoService {

    List<BoletoResponseDTO> findAll();
    List<BoletoResponseDTO> findByCodigoBarras(String codigoBarras);
    BoletoResponseDTO findById(Long id);
    BoletoResponseDTO create(@Valid BoletoDTO dto);
    void update(Long id, @Valid BoletoDTO dto);
    void delete(Long id);
    
}
