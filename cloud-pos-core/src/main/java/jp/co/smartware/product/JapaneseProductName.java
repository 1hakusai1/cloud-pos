package jp.co.smartware.product;

public class JapaneseProductName {

    private final String value;

    public JapaneseProductName(final String value) {
        if (value == null) {
            throw new NullPointerException("JapaneseProductName value is null.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
