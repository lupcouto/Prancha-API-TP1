package br.unitins.topicos1.prancha.resource;
import java.util.List;
import br.unitins.topicos1.prancha.model.Usuario;
import br.unitins.topicos1.prancha.repository.UsuarioRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/usuarios") // todos os métodos dessa classe vão vir com esse caminho
@Produces(MediaType.APPLICATION_JSON) // aplicando o JSON no response e request
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject //instanciando sem precisar instanciar o objeto
    UsuarioRepository repository;

    @GET //CRUD = read (listar todos os usuários)
    public List<Usuario> buscarTodos() {
        return repository.listAll();
    }

    @GET //CRUD = read (listar todos os usuários por nome)
    @Path("/find/{nome}") //encontrando pelo nome
    public List<Usuario> buscarPorNome(@PathParam("nome") String nome) {
        return repository.findByNome(nome);
    }

    @POST //CRUD = create (criando um novo usuário)
    @Transactional
    public Usuario incluir(Usuario usuario) {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuario.getNome());

        repository.persist(novoUsuario);

        return novoUsuario;

    }

    @PUT //CRUD = update (atualizando um usuário)
    @Path("/{id}") //atualizando pelo id
    @Transactional
    public void alterar(Long id, Usuario usuario) {

        Usuario alteracaoUsuario = repository.findById(id);
        alteracaoUsuario.setNome(usuario.getNome());

    }

    @DELETE //CRUD = delete (apagando um usuário)
    @Path("/{id}") //apagando pelo id
    @Transactional
    public void deletar(Long id) {

        repository.deleteById(id);

    }
    
}
