package br.unitins.topicos1.prancha.resource;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import br.unitins.topicos1.prancha.dto.CartaoDTO;
import br.unitins.topicos1.prancha.dto.CartaoResponseDTO;
import br.unitins.topicos1.prancha.service.CartaoService;
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

@Path("/cartoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartaoResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio (service) referente ao cartão
    @Inject
    CartaoService cartaoService;

    // busca todos os cartões cadastrados
    @GET
    @RolesAllowed({"ADM","USER"})
    public List<CartaoResponseDTO> getAll() {
        return cartaoService.findAll();
    }

    // busca todos os boletos cadastrados com um determinado número
    @GET
    @RolesAllowed({"ADM","USER"})
    @Path("/numeroCartao/{numeroCartao}")
    public List<CartaoResponseDTO> getByNumero(@PathParam("numeroCartao") String numeroCartao) {
        return cartaoService.findByNumeroCartao(numeroCartao);
    }

    // cadastra um novo cartão
    @POST
    @RolesAllowed("USER")
    public Response incluir(@Valid CartaoDTO dto) {
        CartaoResponseDTO cartao = cartaoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(cartao).build();
    }

    // altera um boleto já existente
    @PUT
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid CartaoDTO dto) {
        cartaoService.update(id, dto);
        return Response.noContent().build();
    }

    // deleta um boleto já existente
    @DELETE
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        cartaoService.delete(id);
        return Response.noContent().build();
    }
    
}