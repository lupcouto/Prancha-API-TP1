package br.unitins.topicos1.prancha.repository;
import java.util.List;
import br.unitins.topicos1.prancha.model.Fornecedor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {

    // buscando o fornecedor pelo cnpj
    public List<Fornecedor> findByCnpj(String cnpj) {
        return list("cnpj", cnpj);
    }
    
}