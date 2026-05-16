package br.unitins.topicos1.prancha.model;
import jakarta.persistence.Entity;

@Entity
public class Cliente extends Pessoa {

    private String cpf;

    private String email;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}