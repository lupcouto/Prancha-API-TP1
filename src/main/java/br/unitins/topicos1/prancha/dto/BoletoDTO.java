package br.unitins.topicos1.prancha.dto;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

public record BoletoDTO(

    @NotNull(message = "O código de barras é obrigatório")
    String codigoBarras,

    @NotNull(message = "A data de vencimento é obrigatória")
    LocalDate dataVencimento

) {}
