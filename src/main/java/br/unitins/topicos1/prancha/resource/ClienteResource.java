package br.unitins.topicos1.prancha.resource;
import java.util.List;
import br.unitins.topicos1.prancha.dto.ClienteDTO;
import br.unitins.topicos1.prancha.model.Cliente;
import br.unitins.topicos1.prancha.service.ClienteService;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio (service) referente ao cliente
    @Inject
    ClienteService clienteService;

    // busca todos os clientes
    @GET
    @RolesAllowed({"ADM","USER"})
    public List<Cliente> getAll() {
        return clienteService.findAll();
    }

    // busca todos os clientes com um determinado cpf
    @GET
    @RolesAllowed({"ADM","USER"})
    @Path("/cpf/{cpf}")
    public List<Cliente> getByCpf(@PathParam("cpf")String cpf) {
        return clienteService.findByCpf(cpf);
    }

    // cadastra um novo cliente
    @POST
    public Response incluir(@Valid ClienteDTO dto) {
        var cliente = clienteService.create(dto);
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }

    // altera um cliente existente
    @PUT
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid ClienteDTO dto) {
        clienteService.update(id, dto);
        return Response.noContent().build();
    }

    // deleta um cliente existente
    @DELETE
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.noContent().build();
    }
    
}
