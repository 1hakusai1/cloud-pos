package jp.co.smartware.product;

public class InventoryNotEnoughException extends Exception {
    public InventoryNotEnoughException(String message) {
        super(message);
    }
}
