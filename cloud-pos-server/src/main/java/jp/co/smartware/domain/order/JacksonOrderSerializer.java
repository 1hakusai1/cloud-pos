package jp.co.smartware.domain.order;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import jp.co.smartware.domain.product.JANCode;

public class JacksonOrderSerializer extends StdSerializer<Order> {

    public JacksonOrderSerializer(Class<Order> clazz) {
        super(clazz);
    }

    public JacksonOrderSerializer() {
        this(null);
    }

    @Override
    public void serialize(
            Order value,
            JsonGenerator gen,
            SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("orderID", value.getOrderID());
        gen.writeStringField("lpNumber", value.getLpNumber());
        gen.writeStringField("status", value.getStatus().toString());
        gen.writeStringField("orderedTimestamp", value.getOrderedTimestamp().toString());
        if (value.getCompletedTimestamp().isPresent()) {
            gen.writeStringField("completedTimestamp", value.getCompletedTimestamp().get().toString());
        }

        gen.writeFieldName("orderdProducts");
        gen.writeStartArray();
        var janCodes = value.listOrderdProducts();
        var janCodeSerializer = provider.findValueSerializer(JANCode.class);
        for (var janCode : janCodes) {
            int amount = value.getOrderdAmount(janCode);
            gen.writeStartObject();
            gen.writeFieldName("janCode");
            janCodeSerializer.serialize(janCode, gen, provider);
            gen.writeNumberField("amount", amount);
            gen.writeEndObject();
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }

}
