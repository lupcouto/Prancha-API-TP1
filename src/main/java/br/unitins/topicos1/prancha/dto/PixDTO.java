package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotNull;

public record PixDTO(

    @NotNull(message = "A chave é obrigatória")
    String chave

) {}