package br.unitins.topicos1.prancha.repository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import br.unitins.topicos1.prancha.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {
    
    // buscando o telefone pelo número
    public List<Telefone> findByNumero(String numero) {
        return list("numero", numero);
    }

}