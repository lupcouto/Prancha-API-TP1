package br.unitins.topicos1.prancha.model;
import jakarta.persistence.Entity;

@Entity
public class Fornecedor extends Pessoa{

    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
}