package br.unitins.topicos1.prancha.resource;
import java.util.List;
import br.unitins.topicos1.prancha.dto.TipoQuilhaDTO;
import br.unitins.topicos1.prancha.model.TipoQuilha;
import br.unitins.topicos1.prancha.service.TipoQuilhaService;
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

@Path("/tipos-quilha")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoQuilhaResource {
    
    // injetado para que os métodos do resource possam chamar a lógica de negócio (service) referente ao tipo de quilha
    @Inject
    TipoQuilhaService service;

    // busca todos os tipos de quilhas
    @GET
    //@RolesAllowed({"ADM","USER"})
    public List<TipoQuilha> getAll() {
        return service.findAll();
    }

    // busca todos os tipos de quilhas com um determinado nome
    @GET
    //@RolesAllowed({"ADM","USER"})
    @Path("/nome/{nome}")
    public List<TipoQuilha> getByNome(@PathParam("nome")String nome) {
        return service.findByNome(nome);
    }

    // cadastra um novo tipo de quilha
    @POST
    //@RolesAllowed("ADM")
    public Response incluir(@Valid TipoQuilhaDTO dto) {
        var tipoQuilha = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(tipoQuilha).build();
    }   

    // altera um tipo de quilha existente
    @PUT
    //@RolesAllowed("ADM")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid TipoQuilhaDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    // deleta um tipo de quilha existente
    @DELETE
    //@RolesAllowed("ADM")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

}