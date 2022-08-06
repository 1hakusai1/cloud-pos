package jp.co.smartware.infrastructure.hibernate;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OptionalStringAttributeConverter
        implements AttributeConverter<Optional<String>, String> {

    @Override
    public String convertToDatabaseColumn(Optional<String> attribute) {
        return attribute.orElse(null);
    }

    @Override
    public Optional<String> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData);
    }

}
