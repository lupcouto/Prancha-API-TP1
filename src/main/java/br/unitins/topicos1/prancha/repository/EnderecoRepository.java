package br.unitins.topicos1.prancha.repository;
import java.util.List;
import br.unitins.topicos1.prancha.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    // buscando o endereço pelo cep
    public List<Endereco> findByCep(String cep) {
        return list("cep", cep);
    }
    
}
