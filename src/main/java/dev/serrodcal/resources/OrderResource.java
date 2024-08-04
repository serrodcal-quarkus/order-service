package dev.serrodcal.resources;

import dev.serrodcal.entities.Customer;
import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.CustomerRepository;
import dev.serrodcal.repositories.OrderRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@ApplicationScoped
@Path("/v1/orders")
public class OrderResource {
    private static final Logger log = Logger.getLogger(OrderResource.class.getName());

    @Inject
    OrderRepository orderRepository;

    @Inject
    CustomerRepository customerRepository;

    @GET
    @Timeout(250)
    @SessionScoped
    public List<Order> getAllOrders() {
        log.info("OrderResource.getAllOrders()");

        return orderRepository.listAll();
    }

    @GET
    @Path("/{id}")
    @Timeout(250)
    @SessionScoped
    public Order getOrderById(@PathParam("id") Long id) {
        log.info("OrderResource.getOrderById()");

        return orderRepository.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Timeout(250)
    @Transactional
    public void updateOrder(@PathParam("id") Long id, Order order) {
        log.info("OrderResource.updateOrder()");
        log.debug(order.toString());

        this.orderRepository.update(
                "product = :product, quantity = :quantity where id = :id",
                Parameters.with("product", order.product)
                        .and("quantity", order.quantity)
                        .and("id", id)
        );
    }

}
