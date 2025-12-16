package br.unitins.topicos1.prancha.model;
import jakarta.persistence.Entity;

@Entity
public class Administrador extends Pessoa {

    private String cargo;
    private String statusAdm;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getStatusAdm() {
        return statusAdm;
    }

    public void setStatusAdm(String statusAdm) {
        this.statusAdm = statusAdm;
    }
    
}