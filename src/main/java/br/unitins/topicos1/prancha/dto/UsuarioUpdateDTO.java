package br.unitins.topicos1.prancha.dto;

public record UsuarioUpdateDTO(
        String nome,
        String email,
        String ddd,
        String numero,
        String senhaConfirmacao) {
}