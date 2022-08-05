package jp.co.smartware.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jp.co.smartware.domain.order.Order;
import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.stock.Stock;
import jp.co.smartware.infrastructure.repository.OrderRepository;
import jp.co.smartware.infrastructure.repository.StockRepository;

@QuarkusTest
public class OutOfStockCalculatorTest {

    @Inject
    OutOfStockCalculator calculator;

    @Inject
    StockRepository stockRepository;

    @Inject
    OrderRepository orderRepository;

    JANCode janCode1 = new JANCode(1);
    JANCode janCode2 = new JANCode(2);
    JANCode janCode3 = new JANCode(3);

    @BeforeEach
    public void setup() {
        clearRepository();
        Stock stock1 = new Stock(janCode1, 1);
        Stock stock2 = new Stock(janCode2, 2);
        Stock stock3 = new Stock(janCode3, 3);
        registerStocks(List.of(stock1, stock2, stock3));
    }

    @Transactional
    @AfterEach
    public void clearRepository() {
        orderRepository.deleteAll();
        stockRepository.deleteAll();
    }

    @Test
    public void _1つの注文で在庫をオーバーしている商品を検知できる() {
        Order order1 = new Order(11, randomLP(), Map.of(janCode1, 2), LocalDateTime.now());
        registerOrders(List.of(order1));

        List<OutOfStockProduct> products = calculator.list();
        OutOfStockProduct product = find(janCode1, products).get();
        assertEquals(2, product.getTotalOrderedAmount());
        assertEquals(1, product.getLackedAmount());
    }

    @Test
    public void 複数の注文で在庫をオーバーしている商品を検知できる() {
        Order order1 = new Order(11, randomLP(), Map.of(janCode2, 1), LocalDateTime.now());
        Order order2 = new Order(12, randomLP(), Map.of(janCode2, 3), LocalDateTime.now());
        registerOrders(List.of(order1, order2));

        List<OutOfStockProduct> products = calculator.list();
        OutOfStockProduct product = find(janCode2, products).get();
        assertEquals(4, product.getTotalOrderedAmount());
        assertEquals(2, product.getLackedAmount());
    }

    @Test
    public void 在庫数と注文数が一致している場合は在庫不足とみなさない() {
        Order order1 = new Order(11, randomLP(), Map.of(janCode3, 1), LocalDateTime.now());
        Order order2 = new Order(12, randomLP(), Map.of(janCode3, 2), LocalDateTime.now());
        registerOrders(List.of(order1, order2));

        List<OutOfStockProduct> products = calculator.list();
        assertTrue(find(janCode3, products).isEmpty());
    }

    @Transactional
    public void registerStocks(Collection<Stock> stocks) {
        stockRepository.persist(stocks);
    }

    @Transactional
    public void registerOrders(Collection<Order> orders) {
        orderRepository.persist(orders);
    }

    private Optional<OutOfStockProduct> find(JANCode janCode, Collection<OutOfStockProduct> products) {
        return products.stream()
                .filter(product -> product.getJanCode().equals(janCode))
                .findFirst();
    }

    private String randomLP() {
        return UUID.randomUUID().toString();
    }

}
