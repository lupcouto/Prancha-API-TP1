package br.unitins.topicos1.prancha.model;
import java.time.LocalDate;
import jakarta.persistence.Entity;

@Entity
public class Cartao extends Pagamento {

    private String numeroCartao;
    private String nomeTitular;
    private LocalDate dataVencimento;

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }
    
    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    
}
