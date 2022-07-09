package jp.co.smartware.boundary.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import jp.co.smartware.dto.OrderDTO;

public class OrderCSVConverterTest {

    @Test
    public void _1商品のみの注文をCSVからDTOに変換できる() throws IOException {
        List<OrderDTO> list = convertCSV("test1.csv");
        assertEquals(5, list.size());
        OrderDTO deserialized = findById(list, "1000000000000003").get();
        assertEquals("LP30000000000003", deserialized.lpNumber);
        assertEquals(3, deserialized.orderedProducts.get("4000000000003"));
    }

    @Test
    public void 複数の商品が含まれる注文をCSVからDTOに変換できる() throws IOException {
        List<OrderDTO> list = convertCSV("test2.csv");
        assertEquals(3, list.size());
        OrderDTO deserialized = findById(list, "1000000000000002").get();
        assertEquals(2, deserialized.orderedProducts.get("4000000000002"));
        assertEquals(3, deserialized.orderedProducts.get("4000000000003"));
        assertEquals(4, deserialized.orderedProducts.get("4000000000004"));
    }

    private List<OrderDTO> convertCSV(String csvFileName) throws IOException {
        String resourcePath = "/" + csvFileName;
        try (
                InputStream in = OrderCSVConverterTest.class.getResourceAsStream(resourcePath);
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader reader = new BufferedReader(isr);) {
            OrderCSVConverter converter = new OrderCSVConverter();
            return converter.fromCSV(reader);
        }
    }

    private Optional<OrderDTO> findById(List<OrderDTO> list, String orderID) {
        return list.stream().filter(order -> order.orderID.equals(orderID)).findFirst();
    }
}
