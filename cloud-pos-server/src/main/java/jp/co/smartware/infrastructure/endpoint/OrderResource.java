package jp.co.smartware.infrastructure.endpoint;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.input.BOMInputStream;
import org.jboss.logging.Logger;

import jp.co.smartware.domain.order.Order;
import jp.co.smartware.infrastructure.csv.order.OrderCSVConverter;
import jp.co.smartware.infrastructure.form.FileUploadForm;
import jp.co.smartware.infrastructure.repository.OrderRepository;

@Path("/orders")
public class OrderResource {

    @Inject
    Logger logger;

    @Inject
    OrderRepository repository;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Map<String, String> importCSV(FileUploadForm form) throws IOException {
        var fis = new FileInputStream(form.file);
        var bis = new BOMInputStream(fis);
        var isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        var br = new BufferedReader(isr);

        var converter = new OrderCSVConverter();
        List<Order> orders;
        try {
            orders = converter.convert(br);
        } catch (Exception e) {
            logger.error("Error occurs while processing CSV file.", e);
            return Map.of("error", e.getMessage());
        }
        repository.persist(orders);
        return Map.of("message", "ok");
    }
}
