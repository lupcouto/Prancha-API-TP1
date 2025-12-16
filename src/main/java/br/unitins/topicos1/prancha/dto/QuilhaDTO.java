package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record QuilhaDTO (
    
    @NotNull(message = "A descrição é obrigatória")
    String descricaoQuilha, 
    
    @NotNull(message = "O tipo da quilha é obrigatório")
    Long idTipoQuilha
    
) {}