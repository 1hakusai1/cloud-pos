package jp.co.smartware.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

public class InMemoryProductRepositoryTest {

    private JANCode janCode;
    private JapaneseProductName japaneseProductName;
    private ChineseProductName chineseProductName;
    private URL imageURL;

    ProductRepository repository;

    @BeforeEach
    public void setup() throws Exception {
        repository = new InMemoryProductRepository();

        janCode = new JANCode("123456789");
        japaneseProductName = new JapaneseProductName("dummy");
        chineseProductName = new ChineseProductName("dummy");
        imageURL = new URL("https://pruduct.example.com/image.png");
        repository.create(janCode, japaneseProductName, chineseProductName, imageURL, 10);
    }

    @Test
    public void 登録した商品のデータが参照できる() {
        Optional<Product> found = repository.findByJANCode(janCode);
        assertTrue(found.isPresent());
        assertEquals(janCode, found.get().getJanCode());
        assertEquals(10, found.get().getInventoryQuantity());
    }

    @Test
    public void 登録されていないJANコードで検索するとnullを返す() {
        Optional<Product> found = repository.findByJANCode(new JANCode("987654321"));
        assertTrue(found.isEmpty());
    }

    @Test
    public void データを更新できる() throws Exception {
        Product found = repository.findByJANCode(janCode).get();
        found.ship(1);
        repository.update(found);

        Product updated = repository.findByJANCode(janCode).get();
        assertEquals(updated.getInventoryQuantity(), 9);
    }

    @Test
    public void 登録されていない商品を更新しようとすると例外を投げる() {
        Product dummyProduct = mock(Product.class);
        when(dummyProduct.getJanCode()).thenReturn(new JANCode("987654321"));

        assertThrows(ProductRepositoryException.class, () -> {
            repository.update(dummyProduct);
        });
    }

}
