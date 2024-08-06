package dev.serrodcal.resources;

import dev.serrodcal.entities.Customer;
import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.CustomerRepository;
import dev.serrodcal.repositories.OrderRepository;
import dev.serrodcal.resources.dtos.responses.OrderResponse;
import dev.serrodcal.resources.dtos.responses.pagination.Metadata;
import dev.serrodcal.resources.dtos.responses.pagination.PaginatedResponse;
import dev.serrodcal.services.OrderService;
import dev.serrodcal.services.dtos.OrderDTO;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

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
    public PaginatedResponse<List<OrderResponse>> getAllOrders() {
        log.info("OrderResource.getAllOrders()");

        return new PaginatedResponse<>(
                this.orderService.getAll().stream()
                        .map(i -> new OrderResponse(i.id(), i.product(), i.quantity(), i.createdAt(), i.updatedAt()))
                        .toList(),
                new Metadata(0, 0, 0)
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
