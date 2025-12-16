package br.unitins.topicos1.prancha.resource;
import java.util.List;
import br.unitins.topicos1.prancha.dto.PixDTO;
import br.unitins.topicos1.prancha.dto.PixResponseDTO;
import br.unitins.topicos1.prancha.service.PixService;
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

@Path("/pix")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PixResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio (service) referente ao pix
    @Inject
    PixService pixService;

    // busca todos os pix cadastrados
    @GET
    @RolesAllowed({"ADM","USER"})
    public List<PixResponseDTO> getAll() {
        return pixService.findAll();
    }

    // busca todos os pix cadastrados com uma determinada chave
    @GET
    @RolesAllowed({"ADM","USER"})
    @Path("/chave/{chave}")
    public List<PixResponseDTO> getByChave(@PathParam("chave") String chave) {
        return pixService.findByChave(chave);
    }

    // cadastra um novo pix
    @POST
    @RolesAllowed("USER")
    public Response incluir(@Valid PixDTO dto) {
        PixResponseDTO pix = pixService.create(dto);
        return Response.status(Response.Status.CREATED).entity(pix).build();
    }

    // altera um pix já existente
    @PUT
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid PixDTO dto) {
        pixService.update(id, dto);
        return Response.noContent().build();
    }

    // deleta um pix já existente
    @DELETE
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        pixService.delete(id);
        return Response.noContent().build();
    }
    
}