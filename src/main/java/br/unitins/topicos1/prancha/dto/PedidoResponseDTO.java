package br.unitins.topicos1.prancha.dto;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

public record PedidoResponseDTO(

    Long id,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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