package jp.co.smartware.infrastructure.repository.panache;

import java.net.URL;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

@ApplicationScoped
public class PanacheProductRepository extends ProductRepository implements PanacheRepository<PanacheProductEntity> {

    @Override
    public Optional<Product> findByJANCode(JANCode jancode) throws ProductRepositoryException {
        var found = find("janCode", jancode.getValue()).singleResultOptional();
        if (found.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(found.get().toProduct());
        }
    }

    @Override
    public void create(JANCode janCode, JapaneseProductName japaneseProductName, ChineseProductName chineseProductName,
            URL imageURL, int inventoryQuantity) throws ProductRepositoryException {
        var entity = new PanacheProductEntity();
        entity.setJanCode(janCode.getValue());
        entity.setInventoryQuantity(inventoryQuantity);
        if (japaneseProductName != null) {
            entity.setJapaneseProductName(japaneseProductName.getValue());
        }
        if (chineseProductName != null) {
            entity.setChineseProductName(chineseProductName.getValue());
        }
        if (imageURL != null) {
            entity.setImageURL(imageURL.toString());
        }

        persist(entity);

    }

    @Override
    public void update(Product product) throws ProductRepositoryException {
        var entity = find("janCode", product.getJanCode().getValue()).firstResult();
        entity.sync(product);
    }

    @Override
    public void delete(JANCode janCode) throws ProductRepositoryException {
        delete("janCode", janCode.getValue());
    }

    @Override
    public void clear() throws ProductRepositoryException {
        deleteAll();
    }

}
