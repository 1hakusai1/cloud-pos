package jp.co.smartware.usecase.arrive;

import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

public class ProductArrivalUsecase {

    private ProductRepository repository;

    public ProductArrivalUsecase(ProductRepository repository) {
        this.repository = repository;
    }

    public void arrive(JANCode janCode, int amount) throws ProductRepositoryException {
        Product product = repository.findByJANCode(janCode).orElseThrow(
                () -> new ProductRepositoryException("Not Found."));
        product.arrive(amount);
        repository.update(product);
    }

}
