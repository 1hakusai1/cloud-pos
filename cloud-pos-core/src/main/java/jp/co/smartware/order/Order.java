package jp.co.smartware.order;

import java.util.Collections;
import java.util.Map;

import jp.co.smartware.product.JANCode;

public class Order {

    private final OrderID orderID;
    private final LPNumber lpNumber;
    private final Map<JANCode, Integer> orderedProducts;
    private OrderStatus status;

    Order(
            final OrderID orderID,
            final LPNumber lpNumber,
            final Map<JANCode, Integer> orderedProducts) {
        this(orderID, lpNumber, orderedProducts, OrderStatus.WAITING);
    }

    Order(
            final OrderID orderID,
            final LPNumber lpNumber,
            final Map<JANCode, Integer> orderedProducts,
            final OrderStatus status) {
        if (orderID == null || lpNumber == null || orderedProducts == null || status == null) {
            throw new NullPointerException("Some required parameters are null");
        }
        if (orderedProducts.isEmpty()) {
            throw new IllegalArgumentException("Order must contains more than one product");
        }
        this.orderID = orderID;
        this.lpNumber = lpNumber;
        this.orderedProducts = Collections.unmodifiableMap(orderedProducts);
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderID == null) ? 0 : orderID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (orderID == null) {
            if (other.orderID != null)
                return false;
        } else if (!orderID.equals(other.orderID))
            return false;
        return true;
    }

    public void complete() {
        if (status.equals(OrderStatus.SHIPPED)) {
            throw new IllegalStateException("Already shipped");
        }
        status = OrderStatus.SHIPPED;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Map<JANCode, Integer> getOrderdProducts() {
        return orderedProducts;
    }

}
