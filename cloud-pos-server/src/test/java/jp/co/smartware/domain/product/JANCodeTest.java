package jp.co.smartware.domain.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JANCodeTest {

    @Test
    public void 同一と判定される条件() {
        JANCode janCode1 = new JANCode(1000000L);
        JANCode janCode2 = new JANCode(1000000L);
        assertTrue(janCode1.equals(janCode2));
    }

    @Test
    public void 異なると判定される条件() {
        JANCode janCode1 = new JANCode(100000L);
        JANCode janCode2 = new JANCode(100001L);
        assertFalse(janCode1.equals(janCode2));
    }

    @Test
    public void 同一であればHashCodeも同じ() {
        JANCode janCode1 = new JANCode(100000L);
        JANCode janCode2 = new JANCode(100000L);
        assertTrue(janCode1.hashCode() == janCode2.hashCode());
    }

}
