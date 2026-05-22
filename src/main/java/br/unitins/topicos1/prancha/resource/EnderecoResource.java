package br.unitins.topicos1.prancha.resource;
import java.util.List;
import br.unitins.topicos1.prancha.dto.EnderecoDTO;
import br.unitins.topicos1.prancha.model.Endereco;
import br.unitins.topicos1.prancha.service.EnderecoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio
    // (service) referente ao endereço
    @Inject
    EnderecoService enderecoService;

    // busca todos os endereços
    @GET
    @RolesAllowed({ "ADM", "USER" })
    public List<Endereco> getAll() {
        return enderecoService.findAll();
    }

    // busca todos os endereços com um determinado cpf
    @GET
    @RolesAllowed({ "ADM", "USER" })
    @Path("/cep/{cep}")
    public List<Endereco> getByCep(@PathParam("cep") String cep) {
        return enderecoService.findByCep(cep);
    }

    @GET
    @Path("/me")
    @RolesAllowed({ "USER", "ADM" })
    public List<Endereco> getMe(@Context SecurityContext ctx) {

        System.out.println("LOGIN: " + ctx.getUserPrincipal());

        String login = ctx.getUserPrincipal().getName();

        return enderecoService.findByLogin(login);
    }

    // cadastra um novo endereço
    @POST
    @RolesAllowed("USER")
    public Response incluir(@Valid EnderecoDTO dto) {
        Endereco endereco = enderecoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(endereco).build();
    }

    // altera um endereço já existente
    @PUT
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid EnderecoDTO dto) {
        enderecoService.update(id, dto);
        return Response.noContent().build();
    }

    // delete um endereço já existente
    @DELETE
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        enderecoService.delete(id);
        return Response.noContent().build();
    }

}