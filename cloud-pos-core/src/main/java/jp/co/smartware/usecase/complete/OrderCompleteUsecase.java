package jp.co.smartware.usecase.complete;

import java.util.HashSet;
import java.util.Set;

import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.product.InventoryNotEnoughException;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

public class OrderCompleteUsecase {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public OrderCompleteUsecase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void complete(OrderID orderID)
            throws OrderRepositoryException, ProductRepositoryException, InventoryNotEnoughException {
        Order order = orderRepository.findByID(orderID).orElseThrow(() -> new OrderRepositoryException("Not found"));
        Set<JANCode> orderedProductsJancodes = order.getOrderedProducts().keySet();
        Set<Product> changedProducts = new HashSet<>();

        for (JANCode janCode : orderedProductsJancodes) {
            int orderdAmount = order.getOrderedProducts().get(janCode);
            Product product = productRepository.findByJANCode(janCode)
                    .orElseThrow(() -> new ProductRepositoryException("Not found"));
            product.ship(orderdAmount);
            changedProducts.add(product);
        }
        order.complete();
        orderRepository.update(order);
        for (Product product : changedProducts) {
            productRepository.update(product);
        }
    }

}
