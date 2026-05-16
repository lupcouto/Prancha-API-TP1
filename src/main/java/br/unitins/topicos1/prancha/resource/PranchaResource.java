package br.unitins.topicos1.prancha.resource;
import java.util.List;
import br.unitins.topicos1.prancha.dto.PranchaDTO;
import br.unitins.topicos1.prancha.model.Prancha;
import br.unitins.topicos1.prancha.model.TipoPrancha;
import br.unitins.topicos1.prancha.service.PranchaService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pranchas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PranchaResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio
    // (service) referente a prancha
    @Inject
    PranchaService service;

    // busca todas as pranchas
    @GET
    @PermitAll
    public List<Prancha> getAll() {
        return service.findAll();
    }

    // busca todas as pranchas com um determinado tipo
    @GET
    @PermitAll
    @Path("/tipo/{tipoPrancha}")
    public List<Prancha> getByTipoPrancha(@PathParam("tipoPrancha") TipoPrancha tipoPrancha) {
        return service.findByTipoPrancha(tipoPrancha);
    }

    @GET
    @PermitAll
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    // cadastra uma nova prancha
    @POST
    @RolesAllowed("ADM")
    public Response incluir(@Valid PranchaDTO dto) {
        var prancha = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(prancha).build();
    }

    // altera uma prancha existente
    @PUT
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid PranchaDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    // deleta uma prancha existente
    @DELETE
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

}