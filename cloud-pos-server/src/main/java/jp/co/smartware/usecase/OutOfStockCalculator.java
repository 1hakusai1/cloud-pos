package jp.co.smartware.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jp.co.smartware.domain.order.Order;
import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.stock.Stock;
import jp.co.smartware.infrastructure.repository.OrderRepository;
import jp.co.smartware.infrastructure.repository.StockRepository;

@ApplicationScoped
public class OutOfStockCalculator {

    @Inject
    OrderRepository orderRepository;

    @Inject
    StockRepository stockRepository;

    public List<OutOfStockProduct> list() {
        Map<JANCode, Integer> totalOrderedAmounts = calcTotalOrderedAmounts();
        Map<JANCode, Integer> lackedAmounts = calcLackedAmounts(totalOrderedAmounts);
        return listOutofStockProducts(totalOrderedAmounts, lackedAmounts);
    }

    private Map<JANCode, Integer> calcTotalOrderedAmounts() {
        Map<JANCode, Integer> totalOrderedAmount = new HashMap<>();
        List<Order> waitings = orderRepository.listWaiting();
        for (Order order : waitings) {
            for (JANCode janCode : order.getOrderedProducts()) {
                if (!totalOrderedAmount.containsKey(janCode)) {
                    totalOrderedAmount.put(janCode, 0);
                }
                int amount = order.getOrderedAmount(janCode);
                int currentTotal = totalOrderedAmount.get(janCode);
                totalOrderedAmount.put(janCode, currentTotal + amount);
            }
        }
        return totalOrderedAmount;
    }

    private Map<JANCode, Integer> calcLackedAmounts(Map<JANCode, Integer> totalOrderedAmounts) {
        Map<JANCode, Integer> lackedAmount = new HashMap<>();
        for (JANCode janCode : totalOrderedAmounts.keySet()) {
            int orderedAmount = totalOrderedAmounts.get(janCode);
            Stock stock = stockRepository.findById(janCode);
            if (stock.getAmount() < orderedAmount) {
                lackedAmount.put(janCode, orderedAmount - stock.getAmount());
            }
        }
        return lackedAmount;
    }

    private List<OutOfStockProduct> listOutofStockProducts(Map<JANCode, Integer> totalOrderedAmounts,
            Map<JANCode, Integer> lackedAmounts) {
        List<OutOfStockProduct> outOfStockProducts = new ArrayList<>();
        for (JANCode janCode : lackedAmounts.keySet()) {
            OutOfStockProduct outOfStockProduct = new OutOfStockProduct(
                    janCode,
                    totalOrderedAmounts.get(janCode),
                    lackedAmounts.get(janCode));
            outOfStockProducts.add(outOfStockProduct);
        }
        return outOfStockProducts;
    }

}
