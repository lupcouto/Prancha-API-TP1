package br.unitins.topicos1.prancha.dto;

import br.unitins.topicos1.prancha.model.Usuario;

public record UsuarioResponseDTO(

        Long id,
        String login,
        String perfil,

        String nome,
        String email,
        String cpf,

        String ddd,
        String numero

) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {

        if (usuario == null)
            return null;

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getPerfil().name(),

                usuario.getCliente().getNome(),
                usuario.getCliente().getEmail(),
                usuario.getCliente().getCpf(),

                usuario.getCliente().getTelefone().getDdd(),
                usuario.getCliente().getTelefone().getNumero());
    }
}