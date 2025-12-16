package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record FornecedorDTO(

    @NotNull(message = "O nome é obrigatório")
    String nome,

    @NotNull(message = "O DDD é obrigatório")
    String ddd,

    @NotNull(message = "O número é obrigatório")
    String numero,
    
    @NotNull(message = "O cnpj é obrigatório")
    String cnpj
    
) {}
