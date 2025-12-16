package br.unitins.topicos1.prancha.dto;
import br.unitins.topicos1.prancha.model.Usuario;

public record AuthResponseDTO (

    Long id,
    String login,
    String senha

) {  
    
    public static AuthResponseDTO valueOf(Usuario usuario) {
        return new AuthResponseDTO (
            usuario.getId(),
            usuario.getLogin(),
            usuario.getSenha()
        );
    }
}