package br.unitins.topicos1.prancha.model;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Prancha extends DefaultEntity{
    
    private float tamanho;
    private double valor;
    private Integer estoque;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    private TipoPrancha tipoPrancha;

    @Enumerated(EnumType.STRING)
    private Habilidade habilidade;

    @ManyToMany
    @JoinTable(name = "prancha_fornecedor",
        joinColumns = @JoinColumn(name = "id_prancha"),
        inverseJoinColumns = @JoinColumn(name = "id_fornecedor"))
        private List<Fornecedor> fornecedores;

    @ManyToMany
    @JoinTable(name = "prancha_quilha",
        joinColumns = @JoinColumn(name = "id_prancha"),
        inverseJoinColumns = @JoinColumn(name = "id_quilha"))
        private List<Quilha> quilhas;

     public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public TipoPrancha getTipoPrancha() {
        return tipoPrancha;
    }

    public void setTipoPrancha(TipoPrancha tipoPrancha) {
        this.tipoPrancha = tipoPrancha;
    }

    public Habilidade getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(Habilidade habilidade) {
        this.habilidade = habilidade;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public List<Quilha> getQuilhas() {
        return quilhas;
    }

    public void setQuilhas(List<Quilha> quilhas) {
        this.quilhas = quilhas;
    }
}