package dev.serrodcal.resources;

import dev.serrodcal.entities.Order;
import dev.serrodcal.resources.dtos.pagination.PaginatedQuery;
import dev.serrodcal.resources.dtos.responses.OrderResponse;
import dev.serrodcal.resources.dtos.responses.pagination.Metadata;
import dev.serrodcal.resources.dtos.responses.pagination.PaginatedResponse;
import dev.serrodcal.resources.util.CheckParamUtil;
import dev.serrodcal.services.OrderService;
import dev.serrodcal.services.dtos.OrderDTO;
import dev.serrodcal.services.dtos.pagination.PaginatedDTO;
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
                .map(i -> new OrderResponse(i.id(), i.product(), i.quantity(), i.createdAt(), i.updatedAt()))
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
        OrderDTO orderDTO = this.orderService.getById(id);

        return new OrderResponse(
                orderDTO.id(),
                orderDTO.product(),
                orderDTO.quantity(),
                orderDTO.createdAt(),
                orderDTO.updatedAt()
        );
    }

    @PUT
    @Path("/{id}")
    @Timeout(250)
    public void updateOrder(@PathParam("id") Long id, @Valid Order order) {
        log.info("OrderResource.updateOrder()");
        log.debug(order.toString());

        this.orderService.update(id, order);
    }

}
