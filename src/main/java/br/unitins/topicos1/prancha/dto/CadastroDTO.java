package br.unitins.topicos1.prancha.dto;
import jakarta.validation.constraints.NotBlank;

public record CadastroDTO(

        @NotBlank String nome,

        @NotBlank String cpf,

        @NotBlank String email,

        @NotBlank String ddd,

        @NotBlank String numero,

        @NotBlank String login,

        @NotBlank String senha,

        EnderecoDTO endereco

) {

}
