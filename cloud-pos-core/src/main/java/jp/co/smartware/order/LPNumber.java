package jp.co.smartware.order;

public class LPNumber {
    private final String value;

    public LPNumber(final String value) {
        if (value == null) {
            throw new NullPointerException("JPNumber value is null");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
