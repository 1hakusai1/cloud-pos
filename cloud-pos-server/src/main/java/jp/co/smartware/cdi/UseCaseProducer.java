package jp.co.smartware.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.usecase.complete.OrderCompleteUsecase;
import jp.co.smartware.usecase.outofstock.OutOfStockDetector;

@ApplicationScoped
public class UseCaseProducer {

    @Inject
    ProductRepository productRepository;

    @Inject
    OrderRepository orderRepository;

    @Produces
    @ApplicationScoped
    public OutOfStockDetector outOfStockDetector() {
        return new OutOfStockDetector(orderRepository, productRepository);
    }

    @Produces
    @ApplicationScoped
    public OrderCompleteUsecase orderCompleteUsecase() {
        return new OrderCompleteUsecase(orderRepository, productRepository);
    }

}
