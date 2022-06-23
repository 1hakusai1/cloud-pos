package jp.co.smartware.usecase.arrive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.co.smartware.infrastructure.repository.InMemoryProductRepository;
import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

public class ProductArrivalUsecaseTest {

    ProductRepository repository;
    ProductArrivalUsecase usecase;
    JANCode janCode;

    @BeforeEach
    public void setup() throws ProductRepositoryException {
        repository = new InMemoryProductRepository();
        usecase = new ProductArrivalUsecase(repository);
        janCode = new JANCode("123456789");
        repository.create(janCode, new JapaneseProductName("dummy"), new ChineseProductName("dummy"), null, 10);
    }

    @Test
    public void 入荷した分だけ在庫が増える() throws ProductRepositoryException {
        usecase.arrive(janCode, 5);
        Product product = repository.findByJANCode(janCode).get();
        assertEquals(15, product.getInventoryQuantity());
    }

    @Test
    public void 存在しない商品を入荷しようとすると例外を投げる() {
        assertThrows(ProductRepositoryException.class, () -> {
            usecase.arrive(new JANCode("987654321"), 5);
        });
    }

}
