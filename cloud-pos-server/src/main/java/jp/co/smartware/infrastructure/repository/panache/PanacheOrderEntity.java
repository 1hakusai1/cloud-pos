package jp.co.smartware.infrastructure.repository.panache;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderStatus;
import jp.co.smartware.product.JANCode;

@Entity
public class PanacheOrderEntity {

    @Id
    private String orderID;

    private String lpNumber;
    private OrderStatus status;

    @ElementCollection
    private Map<String, Integer> orderedProducts;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getLpNumber() {
        return lpNumber;
    }

    public void setLpNumber(String lpNumber) {
        this.lpNumber = lpNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Map<String, Integer> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(Map<String, Integer> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public Order toOrder() {
        var orderID = new OrderID(this.orderID);
        var lpNumber = new LPNumber(this.lpNumber);
        var orderedProducts = this.orderedProducts.keySet().stream().collect(Collectors.toMap(
                key -> new JANCode(key), key -> this.orderedProducts.get(key)));
        return new Order(orderID, lpNumber, orderedProducts, this.status);
    }

    public void sync(Order order) {
        setLpNumber(order.getLpNumber().getValue());
        setStatus(order.getStatus());
        setLpNumber(order.getLpNumber().getValue());
        setOrderedProducts(order.getOrderedProducts().keySet().stream().collect(Collectors.toMap(
                key -> key.getValue(), key -> order.getOrderedProducts().get(key))));
    }

}
