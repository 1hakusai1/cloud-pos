package jp.co.smartware.usecase;

import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jp.co.smartware.infrastructure.repository.OrderRepository;
import jp.co.smartware.infrastructure.repository.StockRepository;

@ApplicationScoped
public class OrderCompleteUsecase {

    @Inject
    OrderRepository orderRepository;

    @Inject
    StockRepository stockRepository;

    public void complete(long orderID) {
        var order = orderRepository.findById(orderID);
        var stocks = order.getOrderedProducts().stream()
                .map(janCode -> stockRepository.findById(janCode))
                .collect(Collectors.toList());
        order.complete(stocks);
    }

}
