package br.unitins.topicos1.prancha.dto;

import jakarta.validation.constraints.NotNull;

public record AuthDTO(

    @NotNull(message = "O login é obrigatório")
    String login,

    @NotNull(message = "A senha é obrigatória")
    String senha 

) {}