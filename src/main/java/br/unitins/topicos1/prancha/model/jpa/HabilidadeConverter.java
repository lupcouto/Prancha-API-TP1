package br.unitins.topicos1.prancha.model.jpa;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import br.unitins.topicos1.prancha.model.Habilidade;

@Converter(autoApply = true) 
public class HabilidadeConverter implements AttributeConverter<Habilidade, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Habilidade habilidade) {
        return habilidade != null ? habilidade.getId() : null;
    }

    @Override
    public Habilidade convertToEntityAttribute(Integer id) {
        if (id == null) return null;
        for (Habilidade h : Habilidade.values()) {
            if (h.getId().equals(id))
                return h;
        }
        throw new IllegalArgumentException("ID de Habilidade inválido: " + id);
    }
}