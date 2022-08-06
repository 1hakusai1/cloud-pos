package jp.co.smartware.domain.stock;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jp.co.smartware.domain.product.JANCode;

@Entity
@Table(name = "stocks")
public class Stock {

    @EmbeddedId
    private JANCode janCode;

    private int amount;

    protected Stock() {
    }

    public Stock(JANCode janCode, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(janCode + ": Stock amount must be 0 or more.");
        }
        this.janCode = janCode;
        this.amount = amount;
    }

    public void ship(int shippedAmount) {
        if (shippedAmount <= 0) {
            throw new IllegalArgumentException(janCode + ": Shipped amount must be 0 or more.");
        }
        amount -= shippedAmount;
    }

    public void arrive(int arrivedAmount) {
        if (arrivedAmount <= 0) {
            throw new IllegalArgumentException(janCode + ": Arrived amount must be 0 or more.");
        }
        amount += arrivedAmount;
    }

    public int getAmount() {
        return amount;
    }

    public JANCode getJanCode() {
        return janCode;
    }

}
