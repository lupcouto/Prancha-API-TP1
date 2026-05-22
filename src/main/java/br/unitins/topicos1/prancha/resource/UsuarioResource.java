package br.unitins.topicos1.prancha.resource;
import br.unitins.topicos1.prancha.dto.UsuarioResponseDTO;
import br.unitins.topicos1.prancha.dto.UsuarioSenhaDTO;
import br.unitins.topicos1.prancha.dto.UsuarioUpdateDTO;
import br.unitins.topicos1.prancha.model.Usuario;
import br.unitins.topicos1.prancha.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/usuarios")
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Path("/me")
    @RolesAllowed({ "ADM", "USER" })
    public UsuarioResponseDTO getUsuarioLogado(@Context SecurityContext ctx) {

        String login = ctx.getUserPrincipal().getName();

        Usuario usuario = usuarioService.findByLogin(login);

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @PUT
    @Path("/me")
    @RolesAllowed({ "ADM", "USER" })
    @Consumes(MediaType.APPLICATION_JSON)
    public UsuarioResponseDTO atualizarUsuario(
            @Context SecurityContext ctx,
            UsuarioUpdateDTO dto) {

        String login = ctx.getUserPrincipal().getName();

        Usuario usuario = usuarioService.updateUsuario(login, dto);

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @PUT
    @Path("/me/senha")
    @RolesAllowed({ "ADM", "USER" })
    @Consumes(MediaType.APPLICATION_JSON)
    public void alterarSenha(
            @Context SecurityContext ctx,
            UsuarioSenhaDTO dto) {

        String login = ctx.getUserPrincipal().getName();

        usuarioService.alterarSenha(login, dto);
    }
}
