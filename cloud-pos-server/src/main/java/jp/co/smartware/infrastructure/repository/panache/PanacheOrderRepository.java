package jp.co.smartware.infrastructure.repository.panache;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.order.OrderStatus;
import jp.co.smartware.product.JANCode;

@ApplicationScoped
public class PanacheOrderRepository extends OrderRepository implements PanacheRepository<PanacheOrderEntity> {

    @Override
    public Optional<Order> findByID(OrderID orderID) throws OrderRepositoryException {
        var found = find("orderID", orderID.getValue()).singleResultOptional();
        if (found.isEmpty()) {
            return Optional.empty();
        } else {
            var entity = found.get();
            return Optional.of(entity.toOrder());
        }
    }

    @Override
    public List<Order> listAllWaitingOrder() throws OrderRepositoryException {
        return find("status", OrderStatus.WAITING).stream()
                .map(entity -> entity.toOrder())
                .collect(Collectors.toList());
    }

    @Override
    public void create(OrderID orderID, LPNumber lpNumber, Map<JANCode, Integer> orderedProducts)
            throws OrderRepositoryException {
        var entity = new PanacheOrderEntity();
        entity.setOrderID(orderID.getValue());
        entity.setLpNumber(lpNumber.getValue());
        entity.setStatus(OrderStatus.WAITING);
        entity.setOrderedProducts(orderedProducts.keySet().stream()
                .collect(Collectors.toMap(
                        key -> key.getValue(), key -> orderedProducts.get(key))));
        persist(entity);
    }

    @Override
    public void update(Order order) throws OrderRepositoryException {
        var entity = find("orderID", order.getOrderID().getValue()).singleResult();
        entity.sync(order);
    }

}
