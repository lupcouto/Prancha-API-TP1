package br.unitins.topicos1.prancha.dto;
import java.util.List;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO(

    @NotNull(message = "O id do cliente é obrigatório")
    Long idCliente,

    @NotNull(message = "O endereço é obrigatório")
    EnderecoDTO endereco,

    @NotNull(message = "A forma de pagamento é obrigatória")
    String formaPagamento,

    PixDTO pix,
    BoletoDTO boleto,
    CartaoDTO cartao,

    @NotNull(message = "Os itens do pedido são obrigatórios")
    List<ItemPedidoDTO> itens

) {}
