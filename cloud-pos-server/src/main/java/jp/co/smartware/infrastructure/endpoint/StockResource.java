package jp.co.smartware.infrastructure.endpoint;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;

import jp.co.smartware.domain.product.JANCode;
import jp.co.smartware.domain.stock.Stock;
import jp.co.smartware.infrastructure.csv.stock.StockCSVReader;
import jp.co.smartware.infrastructure.csv.stock.StockCSVRow;
import jp.co.smartware.infrastructure.form.FileUploadForm;
import jp.co.smartware.infrastructure.repository.StockRepository;
import jp.co.smartware.usecase.OutOfStockCalculator;
import jp.co.smartware.usecase.OutOfStockProduct;

@Path("/stocks")
public class StockResource {

    @Inject
    Logger logger;

    @Inject
    StockRepository repository;

    @Inject
    OutOfStockCalculator outOfStockCalculator;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> importCSV(FileUploadForm form) throws IOException {
        var fis = new FileInputStream(form.file);
        var isr = new InputStreamReader(fis, Charset.forName("MS932"));
        var br = new BufferedReader(isr);
        var csvReader = new StockCSVReader();
        var rows = csvReader.read(br);

        for (var row : rows) {
            try {
                importStock(row);
            } catch (Exception e) {
                logger.errorv(e, "Error occurs while adding stock. JANCode: {0}", row.getJanCode());
            }
        }
        return Map.of("message", "ok");
    }

    @Transactional
    protected void importStock(StockCSVRow row) {
        var janCode = row.getJanCode();
        var amount = row.getAmount();
        if (amount < 0) {
            logger.errorv("JANCode: {0} has minus amount. Assume 0.", janCode);
            amount = 0;
        }
        var stock = new Stock(new JANCode(janCode), amount);
        repository.persist(stock);
    }

    @GET
    @Path("/outofstock")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OutOfStockProduct> getOutOfStockProducts() {
        return outOfStockCalculator.list();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response arrive(@RestPath long id, Map<String, Integer> body) {
        var janCode = new JANCode(id);
        var stock = repository.findById(janCode);
        var amount = body.get("amount");
        stock.arrive(amount);

        return Response.ok().build();
    }

}
