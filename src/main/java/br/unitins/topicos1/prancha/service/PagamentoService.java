package br.unitins.topicos1.prancha.service;
import br.unitins.topicos1.prancha.model.Pagamento;
import br.unitins.topicos1.prancha.model.StatusPagamento;

public interface PagamentoService {
    Pagamento findById(Long id);
    Pagamento atualizarStatus(Long idPagamento, StatusPagamento novoStatus);
}
