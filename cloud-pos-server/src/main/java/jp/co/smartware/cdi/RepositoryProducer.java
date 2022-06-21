package jp.co.smartware.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import jp.co.smartware.infrastructure.repository.InMemoryOrderRepository;
import jp.co.smartware.infrastructure.repository.InMemoryProductRepository;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.product.ProductRepository;

@ApplicationScoped
public class RepositoryProducer {

    @Produces
    @ApplicationScoped
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }

    @Produces
    @ApplicationScoped
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

}
