package br.unitins.topicos1.prancha.model;

public enum TipoPrancha {

    FISH(1),
    SHORTBOARD(2),
    HIBRIDAS(3),
    GUN(4),
    FUNBOARD(5),
    LONGBOARD(6);

    private final int id;

    private TipoPrancha(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}