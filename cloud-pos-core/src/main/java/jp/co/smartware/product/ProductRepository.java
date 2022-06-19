package jp.co.smartware.product;

import java.net.URL;
import java.util.Optional;

public abstract class ProductRepository {

    abstract public Optional<Product> findByJANCode(final JANCode jancode);

    abstract public void create(
            final JANCode janCode,
            final JapaneseProductName japaneseProductName,
            final ChineseProductName chineseProductName,
            final URL imageURL,
            final int inventoryQuantity);

    abstract public void update(final Product product) throws ProductRepositoryException;

    protected static Product createInstance(
            final JANCode janCode,
            final JapaneseProductName japaneseProductName,
            final ChineseProductName chineseProductName,
            final URL imageURL,
            final int inventoryQuantity) {
        return new Product(janCode, japaneseProductName, chineseProductName, imageURL, inventoryQuantity);
    }

}
