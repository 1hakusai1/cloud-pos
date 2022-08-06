package jp.co.smartware.domain.product;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonProductSerializer extends StdSerializer<Product> {

    public JacksonProductSerializer(Class<Product> clazz) {
        super(clazz);
    }

    public JacksonProductSerializer() {
        this(null);
    }

    @Override
    public void serialize(
            Product value,
            JsonGenerator gen,
            SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("janCode");
        var janCodeSerializer = provider.findValueSerializer(JANCode.class);
        janCodeSerializer.serialize(value.getJanCode(), gen, provider);
        gen.writeStringField("name", value.getName());
        if (value.getImageURL().isPresent()) {
            gen.writeStringField("imageURL", value.getImageURL().get());
        }
        gen.writeEndObject();
    }

}
