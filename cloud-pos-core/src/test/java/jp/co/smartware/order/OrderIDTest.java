package jp.co.smartware.order;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class OrderIDTest {

    @Test
    public void nullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, ()->{
            new OrderID(null);
        });
    }

}
