package jp.co.smartware.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.stock.Stock;

public class OrderTest {

    @Test
    public void 待ち状態で初期化される() {
        Order order = new Order(1L, "LP400000", Map.of(new JANCode(100000L), 1), LocalDateTime.now());
        assertEquals(OrderStatus.WAITING, order.getStatus());
    }

    @Test
    public void 商品の数を0以下で注文することは出来ない() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Order(1L, "LP4000001", Map.of(new JANCode(10000L), 1, new JANCode(20000L), 0), LocalDateTime.now());
        });
    }

    @Test
    public void 在庫を渡して注文を完了できる() {
        JANCode janCode1 = new JANCode(100000L);
        Stock stock1 = new Stock(janCode1, 10);
        JANCode janCode2 = new JANCode(100001L);
        Stock stock2 = new Stock(janCode2, 10);

        Order order = new Order(1L, "LP400000", Map.of(janCode1, 1, janCode2, 2), LocalDateTime.now());
        order.complete(List.of(stock1, stock2));

        assertEquals(OrderStatus.COMPLETE, order.getStatus());
        assertEquals(9, stock1.getAmount());
        assertEquals(8, stock2.getAmount());
    }

    @Test
    public void 必要な在庫を全種類渡さないと完了できない() {
        JANCode janCode1 = new JANCode(100000L);
        Stock stock1 = new Stock(janCode1, 10);
        JANCode janCode2 = new JANCode(100001L);

        Order order = new Order(1L, "LP400000", Map.of(janCode1, 1, janCode2, 2), LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> {
            order.complete(List.of(stock1));
        });

        assertEquals(OrderStatus.WAITING, order.getStatus());
        assertEquals(10, stock1.getAmount());
    }

    @Test
    public void 一つでも在庫の足りない商品がある場合は完了出来ない() {
        JANCode janCode1 = new JANCode(100000L);
        Stock stock1 = new Stock(janCode1, 10);
        JANCode janCode2 = new JANCode(100001L);
        Stock stock2 = new Stock(janCode2, 1);

        Order order = new Order(1L, "LP400000", Map.of(janCode1, 1, janCode2, 2), LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> {
            order.complete(List.of(stock1, stock2));
        });

        assertEquals(OrderStatus.WAITING, order.getStatus());
        assertEquals(10, stock1.getAmount());
        assertEquals(1, stock2.getAmount());
    }

    @Test
    public void 完了したタイムスタンプが保存される() {
        LocalDateTime timestamp = LocalDateTime.now();

        JANCode janCode = new JANCode(1L);
        Stock stock = new Stock(janCode, 10);

        Order order = new Order(2L, "LP400000", Map.of(janCode, 1), LocalDateTime.of(2022, 7, 10, 0, 0));
        order = spy(order);
        when(order.getCurrent()).thenReturn(timestamp);
        order.complete(List.of(stock));

        assertEquals(timestamp, order.getCompletedTimestamp().get());
    }

}
