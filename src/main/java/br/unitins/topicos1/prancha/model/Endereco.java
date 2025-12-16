package br.unitins.topicos1.prancha.model;
import jakarta.persistence.Entity;

@Entity
public class Endereco extends DefaultEntity {

    private String cidade;
    private String estado;
    private String cep;

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
}
