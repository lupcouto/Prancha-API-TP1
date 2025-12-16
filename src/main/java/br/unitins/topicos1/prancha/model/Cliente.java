package br.unitins.topicos1.prancha.model;
import jakarta.persistence.Entity;

@Entity
public class Cliente extends Pessoa {

    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
}