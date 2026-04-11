package br.unitins.topicos1.prancha.repository;

import java.util.List;
import br.unitins.topicos1.prancha.model.Prancha;
import br.unitins.topicos1.prancha.model.TipoPrancha;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PranchaRepository implements PanacheRepository<Prancha> {

    // buscando a prancha pelo tipo de prancha
    public List<Prancha> findByTipoPrancha(TipoPrancha tipoPrancha) {
        return list("tipoPrancha", tipoPrancha);
    }
}