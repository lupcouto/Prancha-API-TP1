package br.unitins.topicos1.prancha.dto;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(

    Long id,
    LocalDateTime dataPedido,
    Double valorTotal,

    ClienteDTO cliente,
    EnderecoDTO endereco,

    String formaPagamento,
    PixResponseDTO pix,
    BoletoResponseDTO boleto,
    CartaoResponseDTO cartao,

    List<ItemPedidoDTO> itens

) {}