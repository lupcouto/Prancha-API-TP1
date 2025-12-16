package br.unitins.topicos1.prancha.model.jpa;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import br.unitins.topicos1.prancha.model.TipoPrancha;

@Converter(autoApply = true)
public class TipoPranchaConverter implements AttributeConverter<TipoPrancha, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoPrancha tipo) {
        return tipo != null ? tipo.getId() : null;
    }

    @Override
    public TipoPrancha convertToEntityAttribute(Integer id) {
        if (id == null) return null;
        for (TipoPrancha t : TipoPrancha.values()) {
            if (t.getId() == id)
                return t;
        }
        throw new IllegalArgumentException("ID de TipoPrancha inválido: " + id);
    }
}