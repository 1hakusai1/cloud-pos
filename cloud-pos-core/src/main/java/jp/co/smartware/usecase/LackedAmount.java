package jp.co.smartware.usecase;

public class LackedAmount {

    private final int value;

    public LackedAmount(final int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("LackedAmount value must be more than 1");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
