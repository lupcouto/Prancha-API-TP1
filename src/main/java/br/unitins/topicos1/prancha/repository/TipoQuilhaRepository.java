package br.unitins.topicos1.prancha.repository;
import java.util.List;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoQuilhaRepository implements PanacheRepository<TipoQuilha> {
    
    // buscando o tipo de quilha pelo nome
    public List<TipoQuilha> findByNome(String nome) {
        return list("nome", nome);
    }

}