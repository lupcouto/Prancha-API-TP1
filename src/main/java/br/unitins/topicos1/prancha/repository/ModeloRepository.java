package br.unitins.topicos1.prancha.repository;

import java.util.List;
import br.unitins.topicos1.prancha.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {

    // buscando o modelo pelo nome
    public List<Modelo> findByNome(String nome) {
        return list("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
    }

    public PanacheQuery<Modelo> findByNomePaginado(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            return findAll(); 
        }

        return find("LOWER(nome) LIKE ?1", "%" + nome.toLowerCase() + "%");
    }

}