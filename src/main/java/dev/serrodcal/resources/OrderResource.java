package dev.serrodcal.resources;

import dev.serrodcal.entities.Customer;
import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.CustomerRepository;
import dev.serrodcal.repositories.OrderRepository;
import dev.serrodcal.services.OrderService;
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
    public List<Order> getAllOrders() {
        log.info("OrderResource.getAllOrders()");

        return this.orderService.getAll();
    }

    @GET
    @Path("/{id}")
    @Timeout(250)
    public Order getOrderById(@PathParam("id") Long id) {
        log.info("OrderResource.getOrderById()");

        return this.orderService.getById(id);
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
