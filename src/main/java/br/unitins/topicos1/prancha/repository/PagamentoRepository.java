package br.unitins.topicos1.prancha.repository;
import br.unitins.topicos1.prancha.model.Pagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

// implementa o PanacheRepository que já possui vários metódos, então essa classe não precisa de uma consulta específica 
public class PagamentoRepository implements PanacheRepository<Pagamento> {
}