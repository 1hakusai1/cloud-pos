package jp.co.smartware.usecase;

import jp.co.smartware.domain.product.JANCode;

public class OutOfStockProduct {

    private JANCode janCode;
    private int totalOrderedAmount;
    private int lackedAmount;

    public OutOfStockProduct(JANCode janCode, int totalOrderedAmount, int lackedAmount) {
        this.janCode = janCode;
        this.totalOrderedAmount = totalOrderedAmount;
        this.lackedAmount = lackedAmount;
    }

    public JANCode getJanCode() {
        return janCode;
    }

    public int getTotalOrderedAmount() {
        return totalOrderedAmount;
    }

    public int getLackedAmount() {
        return lackedAmount;
    }

}
