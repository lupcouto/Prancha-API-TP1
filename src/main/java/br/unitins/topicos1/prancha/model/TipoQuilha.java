package br.unitins.topicos1.prancha.model;
import jakarta.persistence.Entity;

@Entity
public class TipoQuilha extends DefaultEntity{

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}