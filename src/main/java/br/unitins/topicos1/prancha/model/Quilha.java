package br.unitins.topicos1.prancha.model;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;

@Entity
public class Quilha extends DefaultEntity {

    private String descricaoQuilha;

    @ManyToOne
    @JoinColumn(name = "id_tipo_quilha")
    private TipoQuilha tipoQuilha;

    public String getDescricaoQuilha() {
        return descricaoQuilha;
    }

    public void setDescricaoQuilha(String descricaoQuilha) {
        this.descricaoQuilha = descricaoQuilha;
    }
    
    public TipoQuilha getTipoQuilha() {
        return tipoQuilha;
    }

    public void setTipoQuilha(TipoQuilha tipoQuilha) {
        this.tipoQuilha = tipoQuilha;
    }
}