package br.unitins.topicos1.prancha.resource;

import java.util.List;
import br.unitins.topicos1.prancha.dto.FornecedorDTO;
import br.unitins.topicos1.prancha.model.Fornecedor;
import br.unitins.topicos1.prancha.service.FornecedorService;
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

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    // injetado para que os métodos do resource possam chamar a lógica de negócio
    // (service) referente ao fornecedor
    @Inject
    FornecedorService service;

    // busca todos os fornecedores
    @GET
    @PermitAll
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize, @QueryParam("cnpj") String cnpj) {
        return Response.ok(service.findAll(page, pageSize, cnpj)).build();
    }

    // busca todos os fornecedores com um determinado cnpj
    @GET
    @PermitAll
    @Path("/cnpj/{cnpj}")
    public List<Fornecedor> getByCnpj(@PathParam("cnpj") String cnpj) {
        return service.findByCnpj(cnpj);
    }

    @GET
    @PermitAll
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    // cadastra um novo fornecedor
    @POST
    @RolesAllowed("ADM")
    public Response incluir(@Valid FornecedorDTO dto) {
        var fornecedor = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(fornecedor).build();
    }

    // altera um fornecedor já existente
    @PUT
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, @Valid FornecedorDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    // deleta um fornecedor já existente
    @DELETE
    @RolesAllowed("ADM")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

}