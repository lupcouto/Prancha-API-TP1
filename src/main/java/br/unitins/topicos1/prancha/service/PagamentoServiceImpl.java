package br.unitins.topicos1.prancha.service;
import java.time.LocalDateTime;
import br.unitins.topicos1.prancha.exception.ValidationException;
import br.unitins.topicos1.prancha.model.Pagamento;
import br.unitins.topicos1.prancha.model.StatusPagamento;
import br.unitins.topicos1.prancha.repository.PagamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    @Inject
    PagamentoRepository pagamentoRepository;

    // busca todos os registros pelo id no banco
    @Override
    public Pagamento findById(Long id) {

        // validações nos campos obrigatórios
        if (id == null || id <= 0) {
            throw ValidationException.of("idPagamento", "id inválido");
        }

        Pagamento pg = pagamentoRepository.findById(id);

        if (pg == null) {
            throw ValidationException.of("idPagamento", "Pagamento não encontrado");
        }

        return pg;
    }

    // atualiza o status do pagamento, de "pendente" para "pago"
    @Override
    @Transactional
    public Pagamento atualizarStatus(Long idPagamento, StatusPagamento novoStatus) {

        Pagamento pagamento = findById(idPagamento);

        if (novoStatus == null) {
            throw ValidationException.of("statusPagamento", "Status inválido");
        }

        pagamento.setStatusPagamento(novoStatus);

        // quando o status vira "pago", ele registra a data e hora que o pagamento foi feito
        if (novoStatus == StatusPagamento.PAGO) {
            pagamento.setDataPagamento(LocalDateTime.now());
        }

        return pagamento;
    }
}