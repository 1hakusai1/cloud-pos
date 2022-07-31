package jp.co.smartware.devel;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;
import jp.co.smartware.domain.order.Order;
import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.stock.Stock;
import jp.co.smartware.infrastructure.repository.OrderRepository;
import jp.co.smartware.infrastructure.repository.StockRepository;

@ApplicationScoped
public class InitialDataRegister {

    @Inject
    StockRepository stockRepository;

    @Inject
    OrderRepository orderRepository;

    @Transactional
    public void setupData(@Observes StartupEvent event) {
        for (int i = 1; i <= 100; i++) {
            JANCode janCode = new JANCode(i);
            Stock stock = new Stock(janCode, i);
            stockRepository.persist(stock);

            Order order = new Order(i, UUID.randomUUID().toString(), Map.of(janCode, i), LocalDateTime.now());
            orderRepository.persist(order);
        }
    }

}
