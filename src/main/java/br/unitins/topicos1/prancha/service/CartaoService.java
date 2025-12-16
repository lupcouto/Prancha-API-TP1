package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.CartaoDTO;
import br.unitins.topicos1.prancha.dto.CartaoResponseDTO;
import jakarta.validation.Valid;

public interface CartaoService {

    List<CartaoResponseDTO> findAll();
    List<CartaoResponseDTO> findByNumeroCartao(String numeroCartao);
    CartaoResponseDTO findById(Long id);
    CartaoResponseDTO create(@Valid CartaoDTO dto);
    void update(Long id, @Valid CartaoDTO dto);
    void delete(Long id);
    
}
