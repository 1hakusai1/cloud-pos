package jp.co.smartware.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class JANCodeTest {

    @Test
    @SuppressWarnings("unused")
    public void nullで初期化するとNullPointerExceptionを投げる() {
        assertThrows(NullPointerException.class, () -> {
            JANCode janCode = new JANCode(null);
        });
    }

    @Test
    public void 同じ値のJANCodeはequalsと判定される() {
        String value = "123456789";
        JANCode janCode1 = new JANCode(value);
        JANCode janCode2 = new JANCode(value);
        assertTrue(janCode1.equals(janCode2));
    }

    @Test
    public void 異なる値のJANCodeはequalsではない() {
        JANCode janCode1 = new JANCode("123456789");
        JANCode janCode2 = new JANCode("987654321");
        assertFalse(janCode1.equals(janCode2));
    }

    @Test
    public void 同じ値の場合同じhashCodeになる() {
        String value = "123456789";
        JANCode janCode1 = new JANCode(value);
        JANCode janCode2 = new JANCode(value);
        assertEquals(janCode1.hashCode(), janCode2.hashCode());
    }

}
