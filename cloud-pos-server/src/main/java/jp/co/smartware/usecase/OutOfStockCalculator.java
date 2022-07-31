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
        Map<JANCode, Integer> totalOrderdAmounts = calcTotalOrderdAmounts();
        Map<JANCode, Integer> lackedAmounts = calcLackedAmounts(totalOrderdAmounts);
        return listOutofStockProducts(totalOrderdAmounts, lackedAmounts);
    }

    private Map<JANCode, Integer> calcTotalOrderdAmounts() {
        Map<JANCode, Integer> totalOrderdAmount = new HashMap<>();
        List<Order> waitings = orderRepository.listWaiting();
        for (Order order : waitings) {
            for (JANCode janCode : order.listOrderdProducts()) {
                if (!totalOrderdAmount.containsKey(janCode)) {
                    totalOrderdAmount.put(janCode, 0);
                }
                int amount = order.getOrderdAmount(janCode);
                int currentTotal = totalOrderdAmount.get(janCode);
                totalOrderdAmount.put(janCode, currentTotal + amount);
            }
        }
        return totalOrderdAmount;
    }

    private Map<JANCode, Integer> calcLackedAmounts(Map<JANCode, Integer> totalOrderdAmounts) {
        Map<JANCode, Integer> lackedAmount = new HashMap<>();
        for (JANCode janCode : totalOrderdAmounts.keySet()) {
            int orderdAmount = totalOrderdAmounts.get(janCode);
            Stock stock = stockRepository.findById(janCode);
            if (stock.getAmount() < orderdAmount) {
                lackedAmount.put(janCode, orderdAmount - stock.getAmount());
            }
        }
        return lackedAmount;
    }

    private List<OutOfStockProduct> listOutofStockProducts(Map<JANCode, Integer> totalOrderdAmounts,
            Map<JANCode, Integer> lackedAmounts) {
        List<OutOfStockProduct> outOfStockProducts = new ArrayList<>();
        for (JANCode janCode : lackedAmounts.keySet()) {
            OutOfStockProduct outOfStockProduct = new OutOfStockProduct(
                    janCode,
                    totalOrderdAmounts.get(janCode),
                    lackedAmounts.get(janCode));
            outOfStockProducts.add(outOfStockProduct);
        }
        return outOfStockProducts;
    }

}
