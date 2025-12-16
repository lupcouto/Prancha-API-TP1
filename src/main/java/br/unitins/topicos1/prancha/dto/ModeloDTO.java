package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record ModeloDTO (
    
    @NotNull(message = "O nome é obrigatório")
    String nome, 
    
    @NotNull(message = "A marca é obrigatória")
    Long idMarca
    
) {}