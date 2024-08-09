package dev.serrodcal.orders.infrastructure;

import dev.serrodcal.orders.infrastructure.dtos.UpdateOrderRequest;
import dev.serrodcal.orders.shared.mappers.OrderMapper;
import dev.serrodcal.shared.infrastructure.dtos.PaginatedQuery;
import dev.serrodcal.orders.infrastructure.dtos.OrderResponse;
import dev.serrodcal.shared.infrastructure.dtos.Metadata;
import dev.serrodcal.shared.infrastructure.dtos.PaginatedResponse;
import dev.serrodcal.shared.infrastructure.util.CheckParamUtil;
import dev.serrodcal.orders.application.OrderService;
import dev.serrodcal.orders.application.dtos.OrderDTO;
import dev.serrodcal.shared.application.dtos.PaginatedDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Path("/v1/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private static final Logger log = Logger.getLogger(OrderResource.class.getName());

    @Inject
    OrderService orderService;

    @Inject
    OrderMapper mapper;

    @GET
    @Timeout(250)
    public PaginatedResponse<List<OrderResponse>> getAllOrders(
            @QueryParam("page") Optional<Integer> pageParam,
            @QueryParam("size") Optional<Integer> sizeParam
    ) {
        log.info("OrderResource.getAllOrders()");

        Integer page = CheckParamUtil.checkPage(pageParam).orElse(0);
        Integer size = CheckParamUtil.checkSize(sizeParam).orElse(10);

        PaginatedDTO<List<OrderDTO>> paginatedDTO = this.orderService.getAll(new PaginatedQuery(page, size));

        List<OrderResponse> orders = paginatedDTO.dto().stream()
                .map(i -> this.mapper.mapOrderDTOToOrderResponse(i))
                .toList();

        return new PaginatedResponse<>(
                orders,
                new Metadata(paginatedDTO.metadata().page(),
                        paginatedDTO.metadata().size(),
                        paginatedDTO.metadata().total()
                )
        );
    }

    @GET
    @Path("/{id}")
    @Timeout(250)
    public OrderResponse getOrderById(@PathParam("id") Long id) {
        log.info("OrderResource.getOrderById()");

        return this.mapper.mapOrderDTOToOrderResponse(this.orderService.getById(id));
    }

    @PUT
    @Path("/{id}")
    @Timeout(250)
    public void updateOrder(@PathParam("id") Long id, @Valid UpdateOrderRequest updateOrderRequest) {
        log.info("OrderResource.updateOrder()");
        log.debug(updateOrderRequest.toString());

        this.orderService.update(id, this.mapper.mapUpdateOrderRequestToOrderDTO(updateOrderRequest));
    }

}
