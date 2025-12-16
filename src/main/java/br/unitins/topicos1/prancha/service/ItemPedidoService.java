package br.unitins.topicos1.prancha.service;
import java.util.List;
import br.unitins.topicos1.prancha.dto.ItemPedidoDTO;
import br.unitins.topicos1.prancha.model.ItemPedido;
import jakarta.validation.Valid;

public interface ItemPedidoService {

    List<ItemPedido> findAll();
    List<ItemPedido> findByPedido(Long idPedido);
    ItemPedido findById(Long id);
    ItemPedido create(Long idPedido, @Valid ItemPedidoDTO dto);
    void update(Long idItem, Long idPedido, @Valid ItemPedidoDTO dto);
    void delete(Long id);
    
}
