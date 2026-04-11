package br.unitins.topicos1.prancha.resource;
import java.util.Arrays;
import java.util.List;
import br.unitins.topicos1.prancha.dto.TipoPranchaResponseDTO;
import br.unitins.topicos1.prancha.model.TipoPrancha;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

@Path("/tipos-prancha")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoPranchaResource {

    @GET
    public List<TipoPranchaResponseDTO> getAll() {
        return Arrays.stream(TipoPrancha.values())
                .map(tp -> new TipoPranchaResponseDTO(tp.getId(), tp.name()))
                .toList();
    }
}
