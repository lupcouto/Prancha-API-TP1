package br.unitins.topicos1.prancha.resource;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Consumes;
import java.util.List;
import br.unitins.topicos1.prancha.dto.QuilhaDTO;
import br.unitins.topicos1.prancha.model.Quilha;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import br.unitins.topicos1.prancha.repository.TipoQuilhaRepository;
import br.unitins.topicos1.prancha.service.QuilhaService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;

@Path("/quilhas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuilhaResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio
    // (service) referente a quilha
    @Inject
    QuilhaService service;

    // injetado para buscar os tipos de quilha antes de buscar as quilhas pelo tipo
    @Inject
    TipoQuilhaRepository tipoQuilhaRepository;

    // busca todas as quilhas
    @GET
    @PermitAll
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,@QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return Response.ok(service.findAll(page, pageSize)).build();
    }

    // busca todas as quilhas com um determinado tipo
    @GET
    @PermitAll
    @Path("/tipoquilha/{idTipoQuilha}")
    public List<Quilha> getByTipoQuilha(@PathParam("idTipoQuilha") Long id) {
        TipoQuilha tipoQuilha = tipoQuilhaRepository.findById(id);
        return service.findByTipoQuilha(tipoQuilha);
    }

    @GET
    @PermitAll
    @Path("/{id}")
    public Quilha getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    // cadastra uma nova quilha
    @POST
    @RolesAllowed("ADM")
    public Response incluir(@Valid QuilhaDTO dto) {
        var quilha = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(quilha).build();
    }

    // altera uma quilha existente
    @PUT
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid QuilhaDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    // deleta uma quilha existente
    @DELETE
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

}