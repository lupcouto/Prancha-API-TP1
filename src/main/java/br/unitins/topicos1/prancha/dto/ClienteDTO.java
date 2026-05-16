package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO (

    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "O DDD é obrigatório")
    String ddd,

    @NotBlank(message = "O número é obrigatório")
    String numero,

    @NotBlank(message = "O cpf é obrigatório")
    String cpf,

    @NotBlank(message = "O email é obrigatório")
    String email

) {}
