package br.unitins.topicos1.prancha.resource;

import java.util.List;
import br.unitins.topicos1.prancha.dto.PedidoDTO;
import br.unitins.topicos1.prancha.dto.PedidoResponseDTO;
import br.unitins.topicos1.prancha.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio
    // (service) referente ao pedido
    @Inject
    PedidoService pedidoService;

    // busca todos os pedidos
    @GET
    @RolesAllowed({ "ADM", "USER" })
    public List<PedidoResponseDTO> getAll() {
        return pedidoService.findAll();
    }

    // busca o histórico de pedidos feitos por um determinado cliente
    @GET
    @RolesAllowed({ "ADM", "USER" })
    @Path("/cliente/{idCliente}")
    public List<PedidoResponseDTO> getByCliente(@PathParam("idCliente") Long idCliente) {
        return pedidoService.findByCliente(idCliente);
    }

    @GET
    @Path("/me")
    @RolesAllowed({ "ADM", "USER" })
    public List<PedidoResponseDTO> getMe(@Context SecurityContext ctx) {

        String login = ctx.getUserPrincipal().getName();

        return pedidoService.findByLogin(login);
    }

    // cadastra um novo pedido
    @POST
    @RolesAllowed("USER")
    public Response incluir(@Valid PedidoDTO dto) {
        var pedido = pedidoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(pedido).build();
    }

    // altera um pedido existente
    @PUT
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid PedidoDTO dto) {
        pedidoService.update(id, dto);
        return Response.noContent().build();
    }

    // marca o pedido como pago, alterando o status de "pendente" para "pago"
    @PUT
    @RolesAllowed("USER")
    @Path("/{id}/pagar")
    public Response pagar(@PathParam("id") Long id) {
        pedidoService.pagar(id);
        return Response.noContent().build();
    }

    // finaliza o pedido
    @PUT
    @RolesAllowed("USER")
    @Path("/{id}/finalizar")
    @Transactional
    public Response finalizar(@PathParam("id") Long idPedido) {
        pedidoService.finalizar(idPedido);
        return Response.ok("Pedido finalizado com sucesso.").build();
    }

    // deleta um pedido existente
    @DELETE
    @RolesAllowed("USER")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        pedidoService.delete(id);
        return Response.noContent().build();
    }
}