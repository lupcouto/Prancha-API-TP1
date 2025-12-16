package br.unitins.topicos1.prancha.repository;
import java.util.List;
import br.unitins.topicos1.prancha.model.Cliente;
import br.unitins.topicos1.prancha.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    // buscando o pedido pelo cliente, aqui mostra o histórico de pedidos de um determinado cliente
    public List<Pedido> findByCliente(Cliente cliente) {
        return list("cliente", cliente);
    }
    
}