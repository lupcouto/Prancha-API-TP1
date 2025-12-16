package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record MarcaDTO (
    
    @NotNull(message = "O nome é obrigatório")
    String nome, 
    
    @NotNull(message = "O país de origem é obrigatório")
    String paisOrigem
    
) {}