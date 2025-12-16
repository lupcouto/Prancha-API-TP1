package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record UsuarioDTO (

    @NotNull(message = "O login é obrigatório")
    String login,

    @NotNull(message = "A senha é obrigatória")
    String senha,
    
    int idPerfil

) {}