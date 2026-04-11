package br.unitins.topicos1.prancha.resource;
import java.util.Arrays;
import java.util.List;
import jakarta.ws.rs.core.MediaType;
import br.unitins.topicos1.prancha.dto.HabilidadeResponseDTO;
import br.unitins.topicos1.prancha.model.Habilidade;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/habilidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HabilidadeResource {

    @GET
    public List<HabilidadeResponseDTO> getAll() {
        return Arrays.stream(Habilidade.values())
                .map(h -> new HabilidadeResponseDTO(h.getId(), h.name()))
                .toList();
    }
}
