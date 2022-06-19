package jp.co.smartware.product;

public class ChineseProductName {

    private final String value;

    public ChineseProductName(final String value) {
        if (value == null) {
            throw new NullPointerException("ChineseProductName value is null");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
