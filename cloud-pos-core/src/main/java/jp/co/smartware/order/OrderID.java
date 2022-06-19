package jp.co.smartware.order;

public class OrderID {
    private final String value;

    public OrderID(final String value) {
        if (value == null) {
            throw new NullPointerException("OrderID value is null");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
