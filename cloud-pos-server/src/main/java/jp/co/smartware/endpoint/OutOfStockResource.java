package jp.co.smartware.endpoint;

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
import jp.co.smartware.dto.OutOfStockDTO;
import jp.co.smartware.usecase.outofstock.OutOfStockDetector;

@Path("/outofstock")
public class OutOfStockResource {

    @Inject
    OutOfStockDetector outOfStockDetector;

    @Inject
    Template outofstockTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> get() {
        return Uni.createFrom()
                .item(Unchecked.supplier(() -> outOfStockDetector.listOutOfStockProducts()))
                .onItem()
                .transform(list -> list.stream().map(info -> OutOfStockDTO.fromInformation(info))
                        .collect(Collectors.toList()))
                .onItem().transform(list -> outofstockTemplate.data("list", list));
    }
}
