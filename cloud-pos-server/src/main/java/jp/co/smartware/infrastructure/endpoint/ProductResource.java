package jp.co.smartware.infrastructure.endpoint;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import jp.co.smartware.infrastructure.csv.product.ProductCSVReader;
import jp.co.smartware.infrastructure.csv.product.ProductCSVRow;
import jp.co.smartware.infrastructure.form.FileUploadForm;
import jp.co.smartware.infrastructure.repository.ProductRepository;

@Path("/products")
public class ProductResource {

    @Inject
    Logger logger;

    @Inject
    ProductRepository repository;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importCSV(FileUploadForm form) throws IOException {
        var fis = new FileInputStream(form.file);
        var isr = new InputStreamReader(fis, Charset.forName("MS932"));
        var br = new BufferedReader(isr);

        var productCSVReader = new ProductCSVReader();
        var rows = productCSVReader.read(br);
        for (var row : rows) {
            try {
                importProduct(row);
            } catch (Exception e) {
                logger.errorv(e, "Error occurs while processing row. JANCode: {0}", row.janCode);
            }
        }
        return Response.ok().build();
    }

    @Transactional
    protected void importProduct(ProductCSVRow row) {
        var product = row.toProduct();
        repository.persist(product);
    }

}
