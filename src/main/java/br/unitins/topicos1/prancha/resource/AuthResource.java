package br.unitins.topicos1.prancha.resource;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import br.unitins.topicos1.prancha.dto.AuthDTO;
import br.unitins.topicos1.prancha.dto.AuthResponseDTO;
import br.unitins.topicos1.prancha.dto.CadastroDTO;
import br.unitins.topicos1.prancha.model.Cliente;
import br.unitins.topicos1.prancha.model.Perfil;
import br.unitins.topicos1.prancha.model.Telefone;
import br.unitins.topicos1.prancha.model.Usuario;
import br.unitins.topicos1.prancha.repository.ClienteRepository;
import br.unitins.topicos1.prancha.repository.UsuarioRepository;
import br.unitins.topicos1.prancha.service.HashService;
import br.unitins.topicos1.prancha.service.JwtService;
import br.unitins.topicos1.prancha.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ClienteRepository clienteRepository;

    // método para fazer o login
    @POST
    public Response login(AuthDTO dto) {

        LOG.infof("Tentativa de login para o usuário: %s", dto.login());

        try {
            // gera hash da senha digitada
            String hash = hashService.getHashSenha(dto.senha());

            // busca usuário no banco
            Usuario usuario = usuarioService.findByLoginAndSenha(dto.login(), hash);

            if (usuario == null) {
                LOG.warnf("Falha de login: usuário não encontrado ou senha incorreta para %s", dto.login());
                return Response.status(Status.UNAUTHORIZED)
                        .entity("Login ou senha inválidos")
                        .build();
            }

            // gera token JWT
            String token = jwtService.generateJwt(usuario.getLogin(), usuario.getPerfil());

            LOG.infof("Login realizado com sucesso: %s", usuario.getLogin());

            // monta resposta
            return Response.ok()
                    .entity(new AuthResponseDTO(
                            usuario.getId(),
                            usuario.getLogin(),
                            token,
                            usuario.getPerfil().name()))
                    .build();

        } catch (Exception e) {
            LOG.errorf(e, "Erro ao tentar logar o usuário: %s", dto.login());
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno no servidor")
                    .build();
        }
    }

    @POST
    @Path("/cadastro")
    @Transactional
    public Response cadastrar(CadastroDTO dto) {

        // verifica login existente
        if (usuarioService.findByLogin(dto.login()) != null) {
            return Response.status(Status.CONFLICT)
                    .entity("Login já existe")
                    .build();
        }

        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        cliente.setTelefone(telefone);

        clienteRepository.persist(cliente);

        Usuario usuario = new Usuario();

        usuario.setLogin(dto.login());

        usuario.setSenha(
                hashService.getHashSenha(dto.senha()));

        usuario.setPerfil(Perfil.USER);

        usuario.setCliente(cliente);

        usuarioRepository.persist(usuario);

        return Response.status(Status.CREATED).build();
    }
}