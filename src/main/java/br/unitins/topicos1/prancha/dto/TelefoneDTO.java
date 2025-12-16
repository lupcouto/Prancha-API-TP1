package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record TelefoneDTO (
    
    @NotNull(message = "O DDD é obrigatório")
    String ddd, 
    
    @NotNull(message = "O número é obrigatório")
    String numero
    
) {}