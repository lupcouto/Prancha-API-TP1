package br.unitins.topicos1.prancha.repository;

import java.util.List;
import br.unitins.topicos1.prancha.model.Marca;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {

    // buscando a marca pelo nome
    public List<Marca> findByNome(String nome) {
        return list("nome", nome);
    }

    public PanacheQuery<Marca> findByNomePaginado(String nome) {
        if (nome == null || nome.isBlank())
            return findAll();
        return find(
                "LOWER(nome) LIKE ?1",
                "%" + nome.toLowerCase() + "%");
    }
}