package jp.co.smartware.endpoint;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.input.BOMInputStream;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jp.co.smartware.boundary.complete.OrderCompleteRequest;
import jp.co.smartware.boundary.csv.OrderCSVConverter;
import jp.co.smartware.boundary.form.FileUploadFormData;
import jp.co.smartware.dto.OrderDTO;
import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.product.InventoryNotEnoughException;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.ProductRepositoryException;
import jp.co.smartware.usecase.complete.OrderCompleteUsecase;

@Path("/orders")
public class OrderResource {

    @Inject
    OrderRepository repository;

    @Inject
    OrderCompleteUsecase completeUsecase;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<OrderDTO> get(String id) {
        OrderID orderID = new OrderID(id);
        return Uni.createFrom().item(orderID)
                .onItem().transform(Unchecked.function(item -> repository.findByID(item)))
                .onItem().transform(found -> convert(found));
    }

    @Path("/waiting")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<OrderDTO>> listWainting() {
        return Uni.createFrom()
                .item(Unchecked.supplier(() -> repository.listAllWaitingOrder()))
                .onItem()
                .transform(
                        list -> list.stream()
                                .map(order -> OrderDTO.fromOrder(order))
                                .collect(Collectors.toList()));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Uni<Map<String, String>> importCSV(FileUploadFormData data)
            throws OrderRepositoryException, IOException {
        FileInputStream in = new FileInputStream(data.file);
        BOMInputStream bomStream = new BOMInputStream(in);
        InputStreamReader reader = new InputStreamReader(bomStream, "UTF-8");
        OrderCSVConverter converter = new OrderCSVConverter();
        List<OrderDTO> dtos = converter.fromCSV(reader);
        for (OrderDTO dto : dtos) {
            Map<JANCode, Integer> orderedProducts = dto.orderedProducts.entrySet().stream()
                    .collect(Collectors.toMap(e -> new JANCode(e.getKey()), e -> e.getValue()));
            repository.create(new OrderID(dto.orderID), new LPNumber(dto.lpNumber), orderedProducts);
        }
        return Uni.createFrom().item(Map.of("message", "ok"));
    }

    @POST
    @Path("/complete")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Map<String, String>> completeOrders(OrderCompleteRequest request)
            throws OrderRepositoryException, ProductRepositoryException, InventoryNotEnoughException {
        for (String orderIDStr : request.orderID) {
            OrderID orderID = new OrderID(orderIDStr);
            completeUsecase.complete(orderID);
        }
        return Uni.createFrom().item(Map.of("message", "ok"));
    }

    private OrderDTO convert(Optional<Order> found) {
        if (found.isEmpty()) {
            return null;
        }
        Order order = found.get();
        return OrderDTO.fromOrder(order);
    }

}
