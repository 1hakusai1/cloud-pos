package jp.co.smartware.order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import jp.co.smartware.product.JANCode;

public abstract class OrderRepository {

    public abstract Optional<Order> findByID(OrderID orderID);

    public abstract List<Order> listAllWaitingOrder();

    protected Order createInstance(
            final OrderID orderID,
            final LPNumber lpNumber,
            final Map<JANCode, Integer> orderedProducts) {
        return new Order(orderID, lpNumber, orderedProducts);
    }

    protected Order createInstance(
            final OrderID orderID,
            final LPNumber lpNumber,
            final Map<JANCode, Integer> orderedProducts,
            final OrderStatus status) {
        return new Order(orderID, lpNumber, orderedProducts, status);
    }
}
