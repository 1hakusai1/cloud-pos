package jp.co.smartware.endpoint;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jp.co.smartware.boundary.csv.InventoyCSVConverter;
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
    public Uni<ProductDTO> get(String id) {
        JANCode janCode = new JANCode(id);
        return Uni.createFrom().item(janCode)
                .onItem().transform(Unchecked.function(item -> repository.findByJANCode(item)))
                .onItem().transform(found -> convert(found));
    }

    @POST
    @Path("/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Map<String, String>> importInventoryCSV(Reader reader) throws ProductRepositoryException {
        InventoyCSVConverter converter = new InventoyCSVConverter();
        List<ProductDTO> dtos = converter.fromCSV(reader);
        for (ProductDTO dto : dtos) {
            Optional<Product> found = repository.findByJANCode(new JANCode(dto.jancode));
            if (found.isEmpty()) {
                continue;
            }
            Product product = found.get();
            product.setInventoryQuantity(dto.inventoryQuantity);
            repository.update(product);
        }
        return Uni.createFrom().item(Map.of("message", "ok"));
    }

    private ProductDTO convert(Optional<Product> found) {
        if (found.isEmpty()) {
            return null;
        }
        Product product = found.get();
        return ProductDTO.fromProduct(product);
    }

}
