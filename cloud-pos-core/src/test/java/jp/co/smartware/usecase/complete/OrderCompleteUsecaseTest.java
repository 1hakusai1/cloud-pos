package jp.co.smartware.usecase.complete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.co.smartware.infrastructure.repository.InMemoryOrderRepository;
import jp.co.smartware.infrastructure.repository.InMemoryProductRepository;
import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.order.OrderStatus;
import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.InventoryNotEnoughException;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

public class OrderCompleteUsecaseTest {

    ProductRepository productRepository;
    OrderRepository orderRepository;
    OrderCompleteUsecase usecase;
    JANCode janCode1;
    JANCode janCode2;
    OrderID orderID;

    @BeforeEach
    public void setup() throws Exception {
        productRepository = new InMemoryProductRepository();
        orderRepository = new InMemoryOrderRepository();

        janCode1 = new JANCode("100001");
        janCode2 = new JANCode("100002");
        orderID = new OrderID("123456789");

        productRepository.create(janCode1, new JapaneseProductName("dummy"), new ChineseProductName("dummy"), null, 5);
        productRepository.create(janCode2, new JapaneseProductName("dummy"), new ChineseProductName("dummy"), null, 5);

        usecase = new OrderCompleteUsecase(orderRepository, productRepository);
    }

    @Test
    public void 注文のステータスが出荷済みになる() throws Exception {
        orderRepository.create(orderID, new LPNumber("1"), Map.of(janCode1, 1, janCode2, 2));
        usecase.complete(orderID);

        Order order = orderRepository.findByID(orderID).get();
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
    }

    @Test
    public void 存在しない注文を完了しようとすると例外を投げる() throws Exception {
        orderRepository.create(orderID, new LPNumber("1"), Map.of(janCode1, 1, janCode2, 2));
        assertThrows(OrderRepositoryException.class, () -> {
            usecase.complete(new OrderID("987654321"));
        });
    }

    @Test
    public void 在庫が足りない場合は例外を投げる() throws Exception {
        orderRepository.create(orderID, new LPNumber("1"), Map.of(janCode1, 1, janCode2, 6));
        assertThrows(InventoryNotEnoughException.class, () -> {
            usecase.complete(orderID);
        });
    }

    @Test
    public void 扱っていない商品が含まれている場合は例外を投げる() throws Exception {
        orderRepository.create(orderID, new LPNumber("1"), Map.of(new JANCode("300001"), 1));
        assertThrows(ProductRepositoryException.class, () -> {
            usecase.complete(orderID);
        });
    }

    @Test
    public void 在庫が足りない場合は注文のステータスと在庫数は変化しない() throws Exception {
        orderRepository.create(orderID, new LPNumber("1"), Map.of(janCode1, 1, janCode2, 6));
        try {
            usecase.complete(orderID);
        } catch (Exception e) {

        }
        Order order = orderRepository.findByID(orderID).get();
        assertEquals(OrderStatus.WAITING, order.getStatus());
        Product product1 = productRepository.findByJANCode(janCode1).get();
        assertEquals(5, product1.getInventoryQuantity());
        Product product2 = productRepository.findByJANCode(janCode2).get();
        assertEquals(5, product2.getInventoryQuantity());
    }

}
