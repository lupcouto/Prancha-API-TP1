package br.unitins.topicos1.prancha.model;
import jakarta.persistence.Entity;

@Entity
public class Marca extends DefaultEntity {
    
    private String nome;
    private String paisOrigem;

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }


}