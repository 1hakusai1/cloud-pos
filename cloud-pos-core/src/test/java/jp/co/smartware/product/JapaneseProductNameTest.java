package jp.co.smartware.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class JapaneseProductNameTest {

    @Test
    @SuppressWarnings("unused")
    public void nullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            JapaneseProductName name = new JapaneseProductName(null);
        });
    }

    @Test
    public void Stringで表示名を取得できる() {
        String value = "テスト商品名";
        JapaneseProductName name = new JapaneseProductName(value);
        assertEquals(value, name.getValue());
    }

}
