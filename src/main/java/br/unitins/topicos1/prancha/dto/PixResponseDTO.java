package br.unitins.topicos1.prancha.dto;

import java.time.LocalDateTime;
import br.unitins.topicos1.prancha.model.Pix;
import br.unitins.topicos1.prancha.model.StatusPagamento;

public record PixResponseDTO(

    Long id,
    String chave,
    LocalDateTime dataPagamento,
    StatusPagamento statusPagamento

) {

    public PixResponseDTO(Pix entity) {
        this(
            entity.getId(),
            entity.getChave(),
            entity.getDataPagamento(),
            entity.getStatusPagamento()
        );
    }
}
