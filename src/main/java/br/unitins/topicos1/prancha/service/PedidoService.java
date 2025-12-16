package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.PedidoDTO;
import br.unitins.topicos1.prancha.dto.PedidoResponseDTO;
import jakarta.validation.Valid;

public interface PedidoService {

    List<PedidoResponseDTO> findAll();
    List<PedidoResponseDTO> findByCliente(Long idCliente);
    PedidoResponseDTO findById(Long id);
    PedidoResponseDTO create(PedidoDTO dto);
    void update(Long id, @Valid PedidoDTO dto);
    void delete(Long id);
    void pagar(Long id);
    void finalizar(Long idPedido);
    
}