package jp.co.smartware.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.infrastructure.csv.order.OrderCSVConverter;

public class OrderCSVConverterTest {

    OrderCSVConverter converter;

    @BeforeEach
    public void setup() {
        converter = new OrderCSVConverter();
    }

    @Test
    public void 商品が1種類のみの注文データを読み込める() {
        var result = convert("order1.csv");

        assertEquals(3, result.size());
        var order = result.get(0);
        assertEquals(100001L, order.getOrderID());
        assertEquals("LP200001", order.getLpNumber());
        assertEquals(1, order.listOrderdProducts().size());
        assertTrue(order.listOrderdProducts().contains(new JANCode(300001)));
        assertEquals(order.getOrderedTimestamp(), LocalDateTime.of(2022, 6, 1, 7, 14, 1));
    }

    @Test
    public void 複数の商品を含む注文データを読み込める() {
        var result = convert("order2.csv");

        assertEquals(2, result.size());
        var order = result.get(0);
        assertEquals(2, order.listOrderdProducts().size());
        assertTrue(order.listOrderdProducts().contains(new JANCode(300001)));
        assertTrue(order.listOrderdProducts().contains(new JANCode(300002)));
        assertEquals(order.getOrderdAmount(new JANCode(300001)), 1);
        assertEquals(order.getOrderdAmount(new JANCode(300002)), 2);
    }

    private List<Order> convert(String filename) {
        var is = OrderCSVConverter.class.getResourceAsStream("/csv/order/" + filename);
        var isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        var br = new BufferedReader(isr);
        return converter.convert(br);
    }
}
