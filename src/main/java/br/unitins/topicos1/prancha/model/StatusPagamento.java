package br.unitins.topicos1.prancha.model;

public enum StatusPagamento {

    PENDENTE(1),
    PAGO(2);

    private final Integer id;

    private StatusPagamento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}