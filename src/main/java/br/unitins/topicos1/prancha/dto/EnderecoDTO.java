package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO (

    @NotNull(message = "A cidade é obrigatória")
    String cidade, 

    @NotNull(message = "O estado é obrigatório")
    String estado, 

    @NotNull(message = "O CEP é obrigatório")
    String cep,

    @NotNull(message = "O bairro é obrigatório")
    String bairro,

    @NotNull(message = "A quadra é obrigatória")
    String quadra,

    @NotNull(message = "A alameda é obrigatória")
    String alameda,

    @NotNull(message = "O número é obrigatório")
    String numero,

    String complemento
    
) {}
