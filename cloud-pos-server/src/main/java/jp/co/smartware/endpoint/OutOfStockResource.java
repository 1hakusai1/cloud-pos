package jp.co.smartware.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jp.co.smartware.dto.OutOfStockDTO;
import jp.co.smartware.usecase.outofstock.OutOfStockDetector;

@Path("/outofstock")
@Transactional
public class OutOfStockResource {

    @Inject
    OutOfStockDetector outOfStockDetector;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<OutOfStockDTO>> get() {
        return Uni.createFrom()
                .item(Unchecked.supplier(() -> outOfStockDetector.listOutOfStockProducts()))
                .onItem()
                .transform(list -> list.stream().map(info -> OutOfStockDTO.fromInformation(info))
                        .collect(Collectors.toList()));
    }
}
