package jp.co.smartware.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import jp.co.smartware.infrastructure.repository.InMemoryOrderRepository;
import jp.co.smartware.order.OrderRepository;

@ApplicationScoped
public class RepositoryProducer {

    @Produces
    @ApplicationScoped
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

}
