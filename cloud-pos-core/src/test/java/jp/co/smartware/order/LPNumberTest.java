package jp.co.smartware.order;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LPNumberTest {

    @Test
    public void nullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, ()->{
            new LPNumber(null);
        });
    }

}
