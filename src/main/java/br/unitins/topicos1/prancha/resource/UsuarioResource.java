package br.unitins.topicos1.prancha.resource;
import br.unitins.topicos1.prancha.dto.UsuarioResponseDTO;
import br.unitins.topicos1.prancha.model.Usuario;
import br.unitins.topicos1.prancha.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

@Path("/usuarios")
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Path("/me")
    @RolesAllowed({"ADM","USER"})
    public UsuarioResponseDTO getUsuarioLogado(@Context SecurityContext ctx) {

        String login = ctx.getUserPrincipal().getName();

        Usuario usuario = usuarioService.findByLogin(login);

        return UsuarioResponseDTO.valueOf(usuario);
    }
    
}
