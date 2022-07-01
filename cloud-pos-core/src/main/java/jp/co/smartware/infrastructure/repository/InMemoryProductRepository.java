package jp.co.smartware.infrastructure.repository;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

public class InMemoryProductRepository extends ProductRepository {

    private Map<JANCode, Product> repository;

    public InMemoryProductRepository() {
        repository = new ConcurrentHashMap<>();
    }

    @Override
    public void create(JANCode janCode, JapaneseProductName japaneseProductName, ChineseProductName chineseProductName,
            URL imageURL, int inventoryQuantity) throws ProductRepositoryException {
        if (exists(janCode)) {
            throw new ProductRepositoryException("JANCode: " + janCode.getValue() + " alreadyExists");
        }
        Product product = createProductInstance(janCode, japaneseProductName, chineseProductName, imageURL,
                inventoryQuantity);
        repository.put(janCode, product);
    }

    @Override
    public Optional<Product> findByJANCode(JANCode jancode) {
        Product found = repository.get(jancode);
        if (found != null) {
            return Optional.of(copyInstance(found));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void update(Product product) throws ProductRepositoryException {
        JANCode janCode = product.getJanCode();
        if (!exists(janCode)) {
            throw new ProductRepositoryException("No product is found for jancode: " + janCode.getValue());
        }
        repository.put(janCode, copyInstance(product));
    }

    public void deleteAll() {
        repository = new ConcurrentHashMap<>();
    }

    private boolean exists(JANCode janCode) {
        return findByJANCode(janCode).isPresent();
    }

    private Product copyInstance(Product source) {
        return createProductInstance(source.getJanCode(), source.getJapaneseProductName(),
                source.getChineseProductName(), source.getImageURL(), source.getInventoryQuantity());
    }

}
