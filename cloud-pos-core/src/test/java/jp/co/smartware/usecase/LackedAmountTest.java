package jp.co.smartware.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LackedAmountTest {

    @Test
    public void _0以下の数で初期化すると例外を投げる(){
        assertThrows(IllegalArgumentException.class, () ->{
            new LackedAmount(0);
        });
    }

}
