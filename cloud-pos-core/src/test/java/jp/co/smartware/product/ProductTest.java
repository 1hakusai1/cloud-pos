package jp.co.smartware.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {

    JANCode janCode;
    ChineseProductName chineseProductName;
    JapaneseProductName japaneseProductName;
    URL imageURL;

    @BeforeEach
    public void initializeTestParameter() throws Exception {
        janCode = new JANCode("123456789");
        chineseProductName = new ChineseProductName("产品");
        japaneseProductName = new JapaneseProductName("テスト商品名");
        imageURL = new URL("https://dummy.example.com/product.img");
    }

    @Test
    public void JANCodeをnullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            new Product(null, japaneseProductName, chineseProductName, imageURL, 0);
        });
    }

    @Test
    public void 日本語表示名をnullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            new Product(janCode, null, chineseProductName, imageURL, 0);
        });
    }

    @Test
    public void 中国語表示名をnullで初期化すると例外を投げる() {
        assertThrows(NullPointerException.class, () -> {
            new Product(janCode, japaneseProductName, null, imageURL, 0);
        });
    }

    @Test
    public void 画像URLはnullで初期化できる() {
        new Product(janCode, japaneseProductName, chineseProductName, null, 0);
    }

    @Test
    public void 在庫数を0未満で初期化すると例外を投げる() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(janCode, japaneseProductName, chineseProductName, imageURL, -10);
        });
    }

    @Test
    public void JANCodeで同一性を判定する() throws MalformedURLException {
        ChineseProductName differentChineseProductName = new ChineseProductName("different value");
        JapaneseProductName diffeJapaneseProductName = new JapaneseProductName("別の商品名");
        URL differentImageURL = new URL("https://different.example.com/image.png");
        Product product1 = new Product(janCode, japaneseProductName, chineseProductName, imageURL, 0);
        Product product2 = new Product(janCode, diffeJapaneseProductName, differentChineseProductName,
                differentImageURL, 10);
        assertTrue(product1.equals(product2));
    }

    @Test
    public void 出荷数を指定して出荷できる() throws Exception {
        Product product = new Product(janCode, japaneseProductName, chineseProductName, imageURL, 100);
        product.ship(20);
        assertEquals(80, product.getInventoryQuantity());
    }

    @Test
    public void 負の数を出荷することは出来ない() throws Exception {
        Product product = new Product(janCode, japaneseProductName, chineseProductName, imageURL, 100);
        assertThrows(IllegalArgumentException.class, () -> {
            product.ship(-10);
        });
    }

    @Test
    public void 在庫数より多く出荷することは出来ない() throws Exception {
        Product product = new Product(janCode, japaneseProductName, chineseProductName, imageURL, 5);
        assertThrows(InventoryNotEnoughException.class, () -> {
            product.ship(10);
        });
    }

}
