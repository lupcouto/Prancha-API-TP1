package br.unitins.topicos1.prancha.resource;

import java.util.List;
import br.unitins.topicos1.prancha.dto.ModeloDTO;
import br.unitins.topicos1.prancha.model.Modelo;
import br.unitins.topicos1.prancha.service.ModeloService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

@Path("/modelos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModeloResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio
    // (service) referente ao modelo
    @Inject
    ModeloService service;

    // busca todos os modelos
    @GET
    // @RolesAllowed({"ADM","USER"})
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,@QueryParam("pageSize") @DefaultValue("10") int pageSize,@QueryParam("nome") String nome) {
        return Response.ok(service.findAll(page, pageSize, nome)).build();
    }

    // busca todos os modelos com um determinado nome
    @GET
    // @RolesAllowed({"ADM","USER"})
    @Path("/nome/{nome}")
    public List<Modelo> getByNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    // cadastra um modelo novo
    @POST
    // @RolesAllowed("ADM")
    public Response incluir(@Valid ModeloDTO dto) {
        var modelo = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(modelo).build();
    }

    // altera um modelo existente
    @PUT
    // @RolesAllowed("ADM")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid ModeloDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    // deleta um modelo existente
    @DELETE
    // @RolesAllowed("ADM")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}