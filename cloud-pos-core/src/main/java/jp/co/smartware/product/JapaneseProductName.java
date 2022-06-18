package jp.co.smartware.product;

public class JapaneseProductName {

    private String value;

    public JapaneseProductName(String value) {
        if (value == null) {
            throw new NullPointerException("JapaneseProductName value is null.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
