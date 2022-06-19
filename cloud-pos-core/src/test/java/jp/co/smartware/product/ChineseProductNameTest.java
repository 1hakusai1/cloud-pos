package jp.co.smartware.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ChineseProductNameTest {

    @Test
    @SuppressWarnings("unused")
    public void nullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            ChineseProductName name = new ChineseProductName(null);
        });
    }

    @Test
    public void Stringで表示名を取得できる() {
        String value = "产品";
        ChineseProductName name = new ChineseProductName(value);
        assertEquals(value, name.getValue());
    }

}
