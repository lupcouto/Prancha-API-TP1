package br.unitins.topicos1.prancha.resource;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import br.unitins.topicos1.prancha.dto.MarcaDTO;
import br.unitins.topicos1.prancha.model.Marca;
import br.unitins.topicos1.prancha.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {
    
    // injetado para que os métodos do resource possam chamar a lógica de negócio (service) referente ao marca
    @Inject
    MarcaService service;

    // busca todas as marcas
    @GET
    @RolesAllowed({"ADM","USER"})
    public List<Marca> getAll() {
        return service.findAll();
    }

    // busca todas as marcas com um determinado nome
    @GET
    @RolesAllowed({"ADM","USER"})
    @Path("/nome/{nome}")
    public List<Marca> getByNome(@PathParam("nome")String nome) {
        return service.findByNome(nome);
    }

    // cadastra uma nova marca
    @POST
    @RolesAllowed("ADM")
    public Response incluir(@Valid MarcaDTO dto) {
        var marca = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(marca).build();
    }

    // altera uma marca existente
    @PUT
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid MarcaDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    // deleta uma marca existente
    @DELETE
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

}