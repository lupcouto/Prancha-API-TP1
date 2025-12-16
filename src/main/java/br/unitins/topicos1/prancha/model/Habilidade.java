package br.unitins.topicos1.prancha.model;

public enum Habilidade {

    INICIANTE(1),
    INTERMEDIARIO(2),
    AVANCADO(3);

    private final Integer id;

    private Habilidade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
}