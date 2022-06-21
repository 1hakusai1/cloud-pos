package jp.co.smartware.dto;

import java.util.Map;
import java.util.stream.Collectors;

import io.quarkus.qute.TemplateData;
import jp.co.smartware.order.Order;

@TemplateData
public class OrderDTO {
    public String orderID;
    public String lpNumber;
    public Map<String, Integer> orderedProducts;
    public String status;

    public static OrderDTO fromOrder(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.orderID = order.getOrderID().getValue();
        dto.lpNumber = order.getLpNumber().getValue();
        dto.orderedProducts = order.getOrderedProducts().keySet().stream()
                .collect(Collectors.toMap(
                        key -> key.getValue(),
                        key -> order.getOrderedProducts().get(key)));
        dto.status = order.getStatus().toString();
        return dto;
    }
}
