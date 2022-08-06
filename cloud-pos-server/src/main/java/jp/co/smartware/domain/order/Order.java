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
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.stock.Stock;

@Entity
@Table(name = "tenmao_order")
public class Order {

    @Id
    @Column(name = "order_id")
    private long orderID;

    @Column(name = "lp_number")
    private String lpNumber;

    @ElementCollection
    @Column(name = "amount")
    @CollectionTable(name = "ordered_products", joinColumns = @JoinColumn(name = "order_id"))
    private Map<JANCode, Integer> orderedProducts;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "ordered_timestamp")
    private LocalDateTime orderedTimestamp;

    @Column(name = "completed_timestamp")
    private LocalDateTime completedTimestamp;

    protected Order() {
    }

    public Order(long orderID, String lpNumber, Map<JANCode, Integer> orderedProducts, LocalDateTime orderedTimestamp) {
        for (int amount : orderedProducts.values()) {
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
        this.orderedProducts = Collections.unmodifiableMap(orderedProducts);
        this.status = OrderStatus.WAITING;
        this.orderedTimestamp = orderedTimestamp;
        this.completedTimestamp = null;
    }

    public void complete(Collection<Stock> stocks) {
        checkAllProductsArePassed(stocks);
        checkAllStocksAreSufficient(stocks);

        for (JANCode janCode : orderedProducts.keySet()) {
            int orderedAmount = orderedProducts.get(janCode);
            Stock stock = findStock(janCode, stocks).orElseThrow();
            stock.ship(orderedAmount);
        }
        status = OrderStatus.COMPLETE;
        completedTimestamp = getCurrent();
    }

    private void checkAllProductsArePassed(Collection<Stock> stocks) {
        for (JANCode janCode : orderedProducts.keySet()) {
            if (findStock(janCode, stocks).isEmpty()) {
                throw new IllegalArgumentException("orderID " + orderID + ", " + janCode + ": Not passed.");
            }
        }
    }

    private void checkAllStocksAreSufficient(Collection<Stock> stocks) {
        for (JANCode janCode : orderedProducts.keySet()) {
            int orderedAmount = orderedProducts.get(janCode);
            Stock stock = findStock(janCode, stocks).orElseThrow();
            if (stock.getAmount() < orderedAmount) {
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

    public Set<JANCode> getOrderedProducts() {
        return Collections.unmodifiableSet(orderedProducts.keySet());
    }

    public int getOrderedAmount(JANCode janCode) {
        if (!orderedProducts.containsKey(janCode)) {
            throw new IllegalArgumentException(janCode + "is not ordered.");
        }
        return orderedProducts.get(janCode);
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
