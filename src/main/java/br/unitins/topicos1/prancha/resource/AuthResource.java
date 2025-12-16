package br.unitins.topicos1.prancha.resource;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import br.unitins.topicos1.prancha.dto.AuthDTO;
import br.unitins.topicos1.prancha.model.Usuario;
import br.unitins.topicos1.prancha.service.HashService;
import br.unitins.topicos1.prancha.service.JwtService;
import br.unitins.topicos1.prancha.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.jboss.logging.Logger;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    UsuarioService usuarioService;

    // método para fazer o login 
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthDTO dto) {
        LOG.infof("Tentativa de login para o usuário: %s", dto.login());
        String hash = null;
        try {
            hash = hashService.getHashSenha(dto.senha()); // gera o hash da senha
            LOG.debugf("Hash gerado para o usuário %s: %s", dto.login(), hash);

            // procura o usuário
            Usuario usuario = usuarioService.findByLoginAndSenha(dto.login(), hash);

            if (usuario == null) {
                LOG.warnf("Falha de login: usuário não encontrado ou senha incorreta para %s", dto.login());
                return Response.noContent().build();
            }

            // gera o token 
            String token = jwtService.generateJwt(usuario.getLogin(), usuario.getPerfil());
            LOG.infof("Login bem-sucedido para o usuário: %s. Token gerado.", dto.login());

            // retorna o token 
            return Response.ok().header("Authorization", token).build();
        } catch (Exception e) {
            LOG.errorf(e, "Erro ao tentar logar o usuário: %s", dto.login());
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}