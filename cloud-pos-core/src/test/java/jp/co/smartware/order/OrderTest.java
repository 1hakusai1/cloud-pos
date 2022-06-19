package jp.co.smartware.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jp.co.smartware.product.JANCode;

@QuarkusTest
public class OrderTest {

    OrderID orderID;
    LPNumber lpNumber;
    OrderStatus status;
    Map<JANCode, Integer> orderedProducts;

    @BeforeEach
    public void setup() {
        orderID = new OrderID("123456789");
        lpNumber = new LPNumber("000000000");
        status = OrderStatus.WAITING;
        orderedProducts = new HashMap<>();
        orderedProducts.put(new JANCode("1111111111"), 5);
    }

    @Test
    public void 注文番号をnullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            new Order(null, lpNumber, orderedProducts, status);
        });
    }

    @Test
    public void LPNumberをnullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            new Order(orderID, null, orderedProducts, status);
        });
    }

    @Test
    public void 注文商品のMapをnullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            new Order(orderID, lpNumber, null, status);
        });
    }

    @Test
    public void ステータスnullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            new Order(orderID, lpNumber, orderedProducts, null);
        });
    }

    @Test
    public void 注文商品が空の場合例外を投げる() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Order(orderID, lpNumber, new HashMap<>(), status);
        });
    }

    @Test
    public void ステータスを指定しない場合出荷待ちで初期化される() {
        Order order = new Order(orderID, lpNumber, orderedProducts);
        assertEquals(OrderStatus.WAITING, order.getStatus());
    }

    @Test
    public void 出荷するとステータスが出荷済みに変わる() {
        Order order = new Order(orderID, lpNumber, orderedProducts);
        order.complete();
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
    }

    @Test
    public void 既に出荷済みの注文を出荷しようとすると例外を投げる() {
        Order order = new Order(orderID, lpNumber, orderedProducts, OrderStatus.SHIPPED);
        assertThrows(IllegalStateException.class, () -> {
            order.complete();
        });
    }

}
