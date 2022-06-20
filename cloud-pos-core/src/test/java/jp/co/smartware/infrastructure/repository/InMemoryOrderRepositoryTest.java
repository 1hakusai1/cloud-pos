package jp.co.smartware.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.order.OrderStatus;
import jp.co.smartware.product.JANCode;

public class InMemoryOrderRepositoryTest {

    private OrderRepository repository;
    private OrderID orderID;
    private LPNumber lpNumber;
    private JANCode janCode;
    private Map<JANCode, Integer> orderedProducts;

    @BeforeEach
    public void setup() throws Exception {
        repository = new InMemoryOrderRepository();

        orderID = new OrderID("1111");
        lpNumber = new LPNumber("111111");
        janCode = new JANCode("123456789");
        orderedProducts = new HashMap<>();
        orderedProducts.put(janCode, 10);

        repository.create(orderID, lpNumber, orderedProducts);
    }

    @Test
    public void 登録済みの注文を参照できる() throws OrderRepositoryException {
        Optional<Order> found = repository.findByID(orderID);
        assertTrue(found.isPresent());
        assertTrue(found.get().getLpNumber().equals(lpNumber));
    }

    @Test
    public void 同一注文番号の注文は登録できない() {
        assertThrows(OrderRepositoryException.class, () -> {
            repository.create(orderID, lpNumber, orderedProducts);
        });
    }

    @Test
    public void 注文のステータスを更新できる() throws Exception {
        Order found = repository.findByID(orderID).get();
        found.complete();
        repository.update(found);

        Order updated = repository.findByID(orderID).get();
        assertEquals(OrderStatus.SHIPPED, updated.getStatus());
    }

    @Test
    public void 未登録の注文を更新しようとすると例外を投げる() throws Exception {
        Order dummy = mock(Order.class);
        when(dummy.getOrderID()).thenReturn(new OrderID("9999"));

        assertThrows(OrderRepositoryException.class, () -> {
            repository.update(dummy);
        });
    }

    @Test
    public void 出荷されていない注文を取得できる() throws Exception {
        List<Order> waiting = repository.listAllWaitingOrder();
        assertEquals(1, waiting.size());
        Order order = waiting.get(0);
        order.complete();
        repository.update(order);

        List<Order> waitingAfterUpdate = repository.listAllWaitingOrder();
        assertEquals(0, waitingAfterUpdate.size());
    }

}
