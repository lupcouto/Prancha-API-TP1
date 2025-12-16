package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO (

    @NotNull(message = "A cidade é obrigatória")
    String cidade, 

    @NotNull(message = "O estado é obrigatório")
    String estado, 

    @NotNull(message = "O CEP é obrigatório")
    String cep 
    
) {}
