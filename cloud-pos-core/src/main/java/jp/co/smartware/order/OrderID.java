package jp.co.smartware.order;

public class OrderID {
    private final String value;

    public OrderID(final String value) {
        if (value == null) {
            throw new NullPointerException("OrderID value is null");
        }
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderID other = (OrderID) obj;
        if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    public String getValue() {
        return value;
    }
}
