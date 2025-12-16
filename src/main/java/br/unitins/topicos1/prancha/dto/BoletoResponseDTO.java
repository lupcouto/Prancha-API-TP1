package br.unitins.topicos1.prancha.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import br.unitins.topicos1.prancha.model.Boleto;
import br.unitins.topicos1.prancha.model.StatusPagamento;

public record BoletoResponseDTO(

    Long id,
    LocalDateTime dataPagamento,
    StatusPagamento statusPagamento,
    String codigoBarras, 
    LocalDate dataVencimento

) {

    public BoletoResponseDTO(Boleto entity) {
        this(
            entity.getId(),
            entity.getDataPagamento(),
            entity.getStatusPagamento(),
            entity.getCodigoBarras(),
            entity.getDataVencimento()
        );
    }
}
