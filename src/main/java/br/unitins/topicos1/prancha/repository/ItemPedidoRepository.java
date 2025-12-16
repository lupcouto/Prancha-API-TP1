package br.unitins.topicos1.prancha.repository;
import java.util.List;
import br.unitins.topicos1.prancha.model.ItemPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemPedidoRepository implements PanacheRepository<ItemPedido> {

    // buscando os itens do pedido pelo pedido
    public List<ItemPedido> findByPedido(Long idPedido) {
        return list("pedido.id", idPedido);
    }
    
}
