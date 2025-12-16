package br.unitins.topicos1.prancha.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import br.unitins.topicos1.prancha.model.Cartao;
import br.unitins.topicos1.prancha.model.StatusPagamento;

public record CartaoResponseDTO( 

    Long id,
    LocalDateTime dataPagamento,
    StatusPagamento statusPagamento,
    String numeroCartao,
    String nomeTitular,
    LocalDate dataVencimento
) {

    public CartaoResponseDTO(Cartao entity) {
        this(
            entity.getId(),
            entity.getDataPagamento(),
            entity.getStatusPagamento(),
            entity.getNumeroCartao(),
            entity.getNomeTitular(),
            entity.getDataVencimento()
        );
    }
}
