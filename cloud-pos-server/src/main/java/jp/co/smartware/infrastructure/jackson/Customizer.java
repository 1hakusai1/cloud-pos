package jp.co.smartware.infrastructure.jackson;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.quarkus.jackson.ObjectMapperCustomizer;
import jp.co.smartware.domain.order.JacksonOrderSerializer;
import jp.co.smartware.domain.order.Order;
import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.product.JANCodeSerializer;

@ApplicationScoped
public class Customizer implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
        SimpleModule janCodeModule = new SimpleModule();
        janCodeModule.addSerializer(JANCode.class, new JANCodeSerializer());
        objectMapper.registerModule(janCodeModule);

        SimpleModule orderModule = new SimpleModule();
        orderModule.addSerializer(Order.class, new JacksonOrderSerializer());
        objectMapper.registerModule(orderModule);
    }

}
