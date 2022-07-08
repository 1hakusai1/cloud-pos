package jp.co.smartware.endpoint;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jp.co.smartware.boundary.csv.OrderCSVConverter;
import jp.co.smartware.dto.OrderDTO;
import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.product.JANCode;

@Path("/orders")
public class OrderResource {

    @Inject
    OrderRepository repository;

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
    public Uni<Map<String, String>> importCSV(Reader reader) throws OrderRepositoryException {
        OrderCSVConverter converter = new OrderCSVConverter();
        List<OrderDTO> dtos = converter.fromCSV(reader);
        for (OrderDTO dto : dtos) {
            Map<JANCode, Integer> orderedProducts = dto.orderedProducts.entrySet().stream()
                    .collect(Collectors.toMap(e -> new JANCode(e.getKey()), e -> e.getValue()));
            repository.create(new OrderID(dto.orderID), new LPNumber(dto.lpNumber), orderedProducts);
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
