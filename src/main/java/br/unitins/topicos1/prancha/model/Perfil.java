package br.unitins.topicos1.prancha.model;

public enum Perfil {

    ADM (1, "Administrador"),
    USER (2, "Usuário");

    public final Integer ID;
    public final String LABEL;

    Perfil(Integer id, String label) {
        this.ID = id;
        this.LABEL = label;
    }

    public static Perfil valueOf(Integer id) {
        if (id == null)
            return null;
        
        for (Perfil perfil : Perfil.values())
            if (perfil.ID.equals(id))
                return perfil;
        
        throw new IllegalArgumentException("id inválido");
    }

}