package jp.co.smartware.domain.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import jp.co.smartware.domain.product.JANCode;

public class StokTest {

    @Test
    public void 負の数で初期化することは出来ない() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(new JANCode(1L), -10);
        });
    }

    @Test
    public void 個数を指定して出荷できる() {
        Stock stock = new Stock(new JANCode(1L), 10);
        stock.ship(1);
        assertEquals(9, stock.getAmount());
    }

    @Test
    public void _0以下の数を出荷することは出来ない() {
        Stock stock = new Stock(new JANCode(1L), 10);
        assertThrows(IllegalArgumentException.class, () -> {
            stock.ship(0);
        });
    }

    @Test
    public void 個数を指定して入荷できる() {
        Stock stock = new Stock(new JANCode(1L), 10);
        stock.arrive(3);
        assertEquals(13, stock.getAmount());
    }

    @Test
    public void _0以下の数を入荷することは出来ない() {
        Stock stock = new Stock(new JANCode(1L), 10);
        assertThrows(IllegalArgumentException.class, () -> {
            stock.arrive(0);
        });
    }

}
