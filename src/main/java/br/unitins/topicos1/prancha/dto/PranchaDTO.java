package br.unitins.topicos1.prancha.dto;
import br.unitins.topicos1.prancha.model.Habilidade;
import br.unitins.topicos1.prancha.model.TipoPrancha;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PranchaDTO (
    
    @Positive(message = "O tamanho da prancha deve ser positivo")
    float tamanho, 
    
    @DecimalMin(value = "0.01", message = "O valor da prancha deve ser maior que 0")
    double valor, 

    @Min(value = 0, message = "O estoque deve ser zero ou maior")
    int estoque,
    
    @NotNull(message = "O tipo da prancha é obrigatório")
    TipoPrancha tipoPrancha, 
    
    @NotNull(message = "A habilidade é obrigatória")
    Habilidade habilidade, 
    
    @NotNull(message = "O modelo é obrigatório")
    Long idModelo, 
    
    @NotNull(message = "O fornecedor é obrigatório")
    Long idFornecedor, 
    
    @NotNull(message = "A quilha é obrigatória")
    Long idQuilha
    
) {}