package jp.co.smartware.product;

public class ChineseProductName {

    private String value;

    public ChineseProductName(String value) {
        if (value == null) {
            throw new NullPointerException("ChineseProductName value is null");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
