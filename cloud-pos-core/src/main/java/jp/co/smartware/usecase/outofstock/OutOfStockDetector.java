package jp.co.smartware.usecase.outofstock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

public class OutOfStockDetector {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public OutOfStockDetector(OrderRepository orderRepository, ProductRepository productRepository) {
        if (orderRepository == null || productRepository == null) {
            throw new NullPointerException();
        }
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OutOfStockProductInformation> listOutOfStockProducts()
            throws OrderRepositoryException, ProductRepositoryException {
        List<OutOfStockProductInformation> answer = new ArrayList<>();
        Map<JANCode, Integer> totalOrderedAmounts = new HashMap<>();
        List<Order> waitingOrders = orderRepository.listAllWaitingOrder();
        for (Order order : waitingOrders) {
            for (JANCode janCode : order.getOrderedProducts().keySet()) {
                if (!totalOrderedAmounts.containsKey(janCode)) {
                    totalOrderedAmounts.put(janCode, 0);
                }
                int total = totalOrderedAmounts.get(janCode);
                total += order.getOrderedProducts().get(janCode);
                totalOrderedAmounts.put(janCode, total);
            }
        }
        for (JANCode janCode : totalOrderedAmounts.keySet()) {
            int totalOrderedAmount = totalOrderedAmounts.get(janCode);
            Optional<Product> optProduct = productRepository.findByJANCode(janCode);
            if (optProduct.isEmpty()) {
                continue;
            }
            Product product = optProduct.get();
            int currentInventoryQuantity = product.getInventoryQuantity();
            if (currentInventoryQuantity < totalOrderedAmount) {
                int lackedAmount = totalOrderedAmount - currentInventoryQuantity;
                OutOfStockProductInformation info = new OutOfStockProductInformation();
                info.setProduct(product);
                info.setTotalOrderedAmount(totalOrderedAmount);
                info.setLackedAmount(lackedAmount);
                answer.add(info);
            }
        }
        return answer;
    }

}
