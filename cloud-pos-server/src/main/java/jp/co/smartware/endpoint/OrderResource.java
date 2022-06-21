package jp.co.smartware.endpoint;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jp.co.smartware.dto.OrderDTO;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;

@Path("/order")
public class OrderResource {

    @Inject
    OrderRepository repository;

    @Inject
    Template waitingTemplate;

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
    public Uni<TemplateInstance> listWainting() {
        return Uni.createFrom()
                .item(Unchecked.supplier(() -> repository.listAllWaitingOrder()))
                .onItem()
                .transform(
                        list -> list.stream()
                                .map(order -> OrderDTO.fromOrder(order))
                                .collect(Collectors.toList()))
                .onItem().transform(list -> waitingTemplate.data("list", list));
    }

    private OrderDTO convert(Optional<Order> found) {
        if (found.isEmpty()) {
            return null;
        }
        Order order = found.get();
        return OrderDTO.fromOrder(order);
    }

}
