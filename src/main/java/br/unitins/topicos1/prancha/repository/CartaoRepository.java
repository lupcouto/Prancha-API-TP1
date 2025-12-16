package br.unitins.topicos1.prancha.repository;
import java.util.List;
import br.unitins.topicos1.prancha.model.Cartao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CartaoRepository implements PanacheRepository<Cartao> {

    // buscando o cartão pelo número
    public List<Cartao> findByNumeroCartao(String numeroCartao) {
        return list("numeroCartao", numeroCartao);
    }
    
}