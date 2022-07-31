package jp.co.smartware.usecase;

import jp.co.smartware.domain.product.JANCode;

public class OutOfStockProduct {

    private JANCode janCode;
    private int totalOrderdAmount;
    private int lackedAmount;

    public OutOfStockProduct(JANCode janCode, int totalOrderdAmount, int lackedAmount) {
        this.janCode = janCode;
        this.totalOrderdAmount = totalOrderdAmount;
        this.lackedAmount = lackedAmount;
    }

    public JANCode getJanCode() {
        return janCode;
    }

    public int getTotalOrderdAmount() {
        return totalOrderdAmount;
    }

    public int getLackedAmount() {
        return lackedAmount;
    }

}
