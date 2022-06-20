package jp.co.smartware.infrastructure.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.order.OrderStatus;
import jp.co.smartware.product.JANCode;

public class InMemoryOrderRepository extends OrderRepository {

    private Map<OrderID, Order> repository;

    public InMemoryOrderRepository() {
        repository = new ConcurrentHashMap<>();
    }

    @Override
    public void create(OrderID orderID, LPNumber lpNumber, Map<JANCode, Integer> orderedProducts) {
        Order order = createOrderInstance(orderID, lpNumber, orderedProducts);
        repository.put(orderID, order);
    }

    @Override
    public Optional<Order> findByID(OrderID orderID) {
        Order found = repository.get(orderID);
        if (found != null) {
            return Optional.of(found);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> listAllWaitingOrder() {
        return repository.values().stream()
                .filter(order -> order.getStatus().equals(OrderStatus.WAITING))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Order order) throws OrderRepositoryException {
        OrderID orderID = order.getOrderID();
        Optional<Order> current = findByID(orderID);
        if (current.isEmpty()) {
            throw new OrderRepositoryException("Order is not found for orderID: " + orderID.getValue());
        }
        repository.put(orderID, order);
    }

}
