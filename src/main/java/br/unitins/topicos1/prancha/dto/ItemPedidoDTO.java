package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoDTO (

    @NotNull(message = "A Prancha é obrigatória")
    Long idPrancha,

    @Positive(message = "A quantidade deve ser maior que zero")
    Integer quantidade,

    @NotNull(message = "O preço é obrigatório")
    Double precoUnit

) {}