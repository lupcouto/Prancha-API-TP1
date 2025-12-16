package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record AdministradorDTO (

    @NotNull(message = "O nome é obrigatório")
    String nome,

    @NotNull(message = "O DDD é obrigatório")
    String ddd,

    @NotNull(message = "O número é obrigatório")
    String numero, 

    @NotNull(message = "O cargo é obrigatório")
    String cargo,

    @NotNull(message = "O status é obrigatório")
    String statusAdm

) {}