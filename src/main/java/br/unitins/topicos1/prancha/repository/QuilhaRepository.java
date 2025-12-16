package br.unitins.topicos1.prancha.repository;
import java.util.List;
import br.unitins.topicos1.prancha.model.Quilha;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuilhaRepository implements PanacheRepository<Quilha> {
    
    // buscando a quilha pelo tipo de quilha
    public List<Quilha> findByTipoQuilha(TipoQuilha tipoQuilha) {
        return list("tipoQuilha", tipoQuilha);
    }

}