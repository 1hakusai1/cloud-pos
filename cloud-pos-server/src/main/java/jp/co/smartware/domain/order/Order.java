package jp.co.smartware.domain.order;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.stock.Stock;

@Entity
@Table(name = "tenmao_order")
public class Order {

    @Id
    private long orderID;

    private String lpNumber;

    @ElementCollection
    @Column(name = "amount")
    @CollectionTable(name = "orderd_products")
    private Map<JANCode, Integer> orderdProducts;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderedTimestamp;

    private LocalDateTime completedTimestamp;

    protected Order() {
    }

    public Order(long orderID, String lpNumber, Map<JANCode, Integer> orderdProducts, LocalDateTime orderedTimestamp) {
        for (int amount : orderdProducts.values()) {
            if (amount <= 0) {
                throw new IllegalArgumentException(
                        "orderID " + orderID + ": Amount of ordered products must be more than 0.");
            }
        }
        if (orderedTimestamp == null) {
            throw new IllegalAccessError("Ordered timestamp must be non null.");
        }
        this.orderID = orderID;
        this.lpNumber = lpNumber;
        this.orderdProducts = Collections.unmodifiableMap(orderdProducts);
        this.status = OrderStatus.WAITING;
        this.orderedTimestamp = orderedTimestamp;
        this.completedTimestamp = null;
    }

    public void complete(Collection<Stock> stocks) {
        checkAllProductsArePassed(stocks);
        checkAllStocksAreSufficient(stocks);

        for (JANCode janCode : orderdProducts.keySet()) {
            int orderdAmount = orderdProducts.get(janCode);
            Stock stock = findStock(janCode, stocks).orElseThrow();
            stock.ship(orderdAmount);
        }
        status = OrderStatus.COMPLETE;
        completedTimestamp = getCurrent();
    }

    private void checkAllProductsArePassed(Collection<Stock> stocks) {
        for (JANCode janCode : orderdProducts.keySet()) {
            if (findStock(janCode, stocks).isEmpty()) {
                throw new IllegalArgumentException("orderID " + orderID + ", " + janCode + ": Not passed.");
            }
        }
    }

    private void checkAllStocksAreSufficient(Collection<Stock> stocks) {
        for (JANCode janCode : orderdProducts.keySet()) {
            int orderdAmount = orderdProducts.get(janCode);
            Stock stock = findStock(janCode, stocks).orElseThrow();
            if (stock.getAmount() < orderdAmount) {
                throw new IllegalArgumentException(
                        "orderID " + orderID + ", " + janCode + ": Not sufficient stok.");
            }
        }
    }

    private Optional<Stock> findStock(JANCode janCode, Collection<Stock> stocks) {
        return stocks.stream()
                .filter((stock) -> stock.getJanCode().equals(janCode))
                .findFirst();
    }

    OrderStatus getStatus() {
        return status;
    }

    public Set<JANCode> listOrderdProducts() {
        return Collections.unmodifiableSet(orderdProducts.keySet());
    }

    public int getOrderdAmount(JANCode janCode) {
        if (!orderdProducts.containsKey(janCode)) {
            throw new IllegalArgumentException(janCode + "is not orderd.");
        }
        return orderdProducts.get(janCode);
    }

    Optional<LocalDateTime> getCompletedTimestamp() {
        return Optional.ofNullable(completedTimestamp);
    }

    LocalDateTime getCurrent() {
        return LocalDateTime.now();
    }

    long getOrderID() {
        return orderID;
    }

    String getLpNumber() {
        return lpNumber;
    }

    LocalDateTime getOrderedTimestamp() {
        return orderedTimestamp;
    }

}
