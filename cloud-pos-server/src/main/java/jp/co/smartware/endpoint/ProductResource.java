package jp.co.smartware.endpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jp.co.smartware.boundary.arrival.ProductArrivalRequest;
import jp.co.smartware.boundary.csv.InventoyCSVConverter;
import jp.co.smartware.boundary.csv.ProductInfoCSVConverter;
import jp.co.smartware.boundary.form.FileUploadFormData;
import jp.co.smartware.dto.ProductDTO;
import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.Product;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;
import jp.co.smartware.usecase.arrive.ProductArrivalUsecase;

@Path("/products")
@Transactional
public class ProductResource {

    @Inject
    ProductRepository repository;

    @Inject
    ProductArrivalUsecase arrivalUsecase;

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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Uni<Map<String, String>> importInventoryCSV(FileUploadFormData data)
            throws ProductRepositoryException, IOException {
        FileInputStream in = new FileInputStream(data.file);
        InputStreamReader reader = new InputStreamReader(in, Charset.forName("MS932"));
        InventoyCSVConverter converter = new InventoyCSVConverter();
        List<ProductDTO> dtos = converter.fromCSV(reader);
        for (ProductDTO dto : dtos) {
            Optional<Product> found = repository.findByJANCode(new JANCode(dto.jancode));
            if (found.isEmpty()) {
                continue;
            }
            Product product = found.get();
            product.setInventoryQuantity(dto.inventoryQuantity);
            try {
                repository.update(product);
            } catch (IllegalArgumentException e) {
                // skip
            }
        }
        return Uni.createFrom().item(Map.of("message", "ok"));
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Uni<Map<String, String>> importProductInfoCSV(FileUploadFormData data)
            throws ProductRepositoryException, IOException {
        File file = data.file;
        FileInputStream in = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(in, Charset.forName("MS932"));

        ProductInfoCSVConverter converter = new ProductInfoCSVConverter();
        List<ProductDTO> dtos = converter.fromCSV(isr);
        for (ProductDTO dto : dtos) {
            int currentInventory = 0;
            Optional<Product> found = repository.findByJANCode(new JANCode(dto.jancode));
            if (found.isPresent()) {
                Product product = found.get();
                currentInventory = product.getInventoryQuantity();
                repository.delete(product.getJanCode());
            }
            JANCode janCode = new JANCode(dto.jancode);
            JapaneseProductName japaneseProductName = null;
            if (dto.japaneseProductName != null) {
                japaneseProductName = new JapaneseProductName(dto.japaneseProductName);
            }
            ChineseProductName chineseProductName = null;
            if (dto.chineseProductName != null) {
                chineseProductName = new ChineseProductName(dto.chineseProductName);
            }
            URL imageURL = null;
            if (dto.imageURL != null && !dto.imageURL.isEmpty()) {
                try {
                    imageURL = new URL(dto.imageURL);
                } catch (MalformedURLException e) {
                    // skip
                }
            }
            repository.create(janCode, japaneseProductName, chineseProductName, imageURL, currentInventory);
        }
        return Uni.createFrom().item(Map.of("message", "ok"));
    }

    @POST
    @Path("/arrival")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Map<String, String>> productArrival(ProductArrivalRequest request) throws ProductRepositoryException {
        JANCode janCode = new JANCode(request.janCode);
        arrivalUsecase.arrive(janCode, request.amount);
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
