package jp.co.smartware.dto;

import java.util.Map;
import java.util.stream.Collectors;

import jp.co.smartware.order.Order;

public class OrderDTO {
    public String orderID;
    public String lpNumber;
    public Map<String, Integer> orderedProducts;

    public static OrderDTO fromOrder(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.orderID = order.getOrderID().getValue();
        dto.lpNumber = order.getLpNumber().getValue();
        dto.orderedProducts = order.getOrderedProducts().keySet().stream()
                .collect(Collectors.toMap(
                        key -> key.getValue(),
                        key -> order.getOrderedProducts().get(key)));
        return dto;
    }
}
