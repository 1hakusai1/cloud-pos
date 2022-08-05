package jp.co.smartware.domain.product;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JANCodeSerializer extends StdSerializer<JANCode> {

    public JANCodeSerializer() {
        this(null);
    }

    public JANCodeSerializer(Class<JANCode> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(
            JANCode value,
            JsonGenerator gen,
            SerializerProvider provider) throws IOException {
        gen.writeNumber(value.getValue());
    }

}
