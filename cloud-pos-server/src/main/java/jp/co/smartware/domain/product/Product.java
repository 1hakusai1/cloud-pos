package jp.co.smartware.domain.product;

import java.util.Optional;

import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import jp.co.smartware.infrastructure.hibernate.OptionalStringAttributeConverter;

@Entity
public class Product {

    @EmbeddedId
    private JANCode janCode;

    private String name;

    @Convert(converter = OptionalStringAttributeConverter.class)
    private Optional<String> imageURL;

    protected Product() {
    }

    public Product(JANCode janCode, String name, String imageURL) {
        if (janCode == null || name == null) {
            throw new IllegalArgumentException("JANCode and product name must be non null value.");
        }
        this.janCode = janCode;
        this.name = name;
        this.imageURL = Optional.ofNullable(imageURL);
    }

    JANCode getJanCode() {
        return janCode;
    }

    String getName() {
        return name;
    }

    Optional<String> getImageURL() {
        return imageURL;
    }

}
