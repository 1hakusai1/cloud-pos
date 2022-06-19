package jp.co.smartware.usecase.outofstock;

import jp.co.smartware.product.Product;

public class OutOfStockProductInformation {

    private Product product;
    private int totalOrderedAmount;
    private int lackedAmount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTotalOrderedAmount() {
        return totalOrderedAmount;
    }

    public void setTotalOrderedAmount(int totalOrderedAmount) {
        this.totalOrderedAmount = totalOrderedAmount;
    }

    public int getLackedAmount() {
        return lackedAmount;
    }

    public void setLackedAmount(int lackedAmount) {
        this.lackedAmount = lackedAmount;
    }

}
