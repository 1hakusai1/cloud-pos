package jp.co.smartware.endpoint;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jp.co.smartware.dto.ProductDTO;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

@Path("/product")
public class ProductResource {

    @Inject
    ProductRepository repository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDTO get(String id) throws ProductRepositoryException {
        Optional<Product> found = repository.findByJANCode(new JANCode(id));
        if (found.isEmpty()) {
            return null;
        } else {
            Product product = found.get();
            ProductDTO dto = new ProductDTO();
            dto.jancode = product.getJanCode().getValue();
            dto.japaneseProductName = product.getJapaneseProductName().getValue();
            dto.chineseProductName = product.getChineseProductName().getValue();
            dto.url = product.getImageURL() != null ? product.getImageURL().toString() : null;
            dto.inventoryQuantity = product.getInventoryQuantity();
            return dto;
        }
    }

}
