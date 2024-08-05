package dev.serrodcal.resources;

import dev.serrodcal.entities.Customer;
import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.CustomerRepository;
import dev.serrodcal.repositories.OrderRepository;
import dev.serrodcal.services.CustomerService;
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
@Path("/v1/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private static final Logger log = Logger.getLogger(CustomerResource.class.getName());

    @Inject
    CustomerService customerService;

    @GET
    @Timeout(250)
    public List<Customer> getAllCustomers() {
        log.info("CustomerResource.getAllCustomers()");

        return this.customerService.getAll();
    }

    @GET
    @Path("/{id}")
    @Timeout
    public Customer getCustomerById(@PathParam("id") Long id) {
        log.info("CustomerResource.getCustomerById()");

        return customerService.getById(id);
    }

    @POST
    @ResponseStatus(201)
    @Timeout(250)
    public Customer createCustomer(Customer customer) {
        log.info("CustomerResource.createCustomer()");
        log.debug(customer.toString());

        this.customerService.save(customer);
        return this.customerService.getById(customer.id);
    }

    @PUT
    @Path("/{id}")
    @Timeout(250)
    public void updateCustomer(@PathParam("id") Long id, Customer customer) {
        log.info("CustomerResource.updateCustomer()");
        log.debug(customer.toString());

        this.customerService.update(id, customer);
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(204)
    @Timeout(250)
    public void deleteCustomer(@PathParam("id") Long id) {
        log.info("CustomerResource.deleteCustomer()");

        this.customerService.deleteById(id);
    }

    @POST
    @Path("/{customerId}/orders")
    @ResponseStatus(201)
    @Timeout(250)
    public void addOrder(@PathParam("customerId") Long customerId, @Valid Order order) {
        log.info("CustomerResource.addOrder()");
        log.debug(order.toString());

        this.customerService.addOrder(customerId, order);
    }

    @DELETE
    @Path("/{customerId}/orders/{orderId}")
    @ResponseStatus(204)
    @Timeout(250)
    public void deleteOrder(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId) {
        log.info("CustomerResource.deleteOrder()");

        this.customerService.deleteOrder(customerId, orderId);
    }

}
