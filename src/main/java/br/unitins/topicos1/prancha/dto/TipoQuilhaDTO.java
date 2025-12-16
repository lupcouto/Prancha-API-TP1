package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record TipoQuilhaDTO (
    
    @NotNull(message = "O nome é obrigatório")
    String nome
    
) {}