package br.unitins.topicos1.prancha.model;
import jakarta.persistence.Entity;

@Entity
public class Pix extends Pagamento {
    
    private String chave;

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
    
}