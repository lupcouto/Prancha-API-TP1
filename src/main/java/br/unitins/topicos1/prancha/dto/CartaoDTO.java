package br.unitins.topicos1.prancha.dto;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

public record CartaoDTO (

    @NotNull(message = "O número é obrigatório")
    String numeroCartao,

    @NotNull(message = "O nome é obrigatório")
    String nomeTitular,

    @NotNull(message = "a data de vencimento é obrigatória")
    LocalDate dataVencimento

) {}
