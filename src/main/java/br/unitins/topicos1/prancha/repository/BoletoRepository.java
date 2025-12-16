package br.unitins.topicos1.prancha.repository;
import java.util.List;
import br.unitins.topicos1.prancha.model.Boleto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BoletoRepository implements PanacheRepository<Boleto> {

    // buscando o boleto pelo código de barras
    public List<Boleto> findByCodigoBarras(String codigoBarras) {
        return list("codigoBarras", codigoBarras);
    }
    
}