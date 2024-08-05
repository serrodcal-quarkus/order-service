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
    CustomerRepository customerRepository;

    @Inject
    OrderRepository orderRepository;

    @GET
    @Timeout(250)
    @SessionScoped
    public List<Customer> getAllCustomers() {
        log.info("CustomerResource.getAllCustomers()");

        return this.customerRepository.listAll();
    }

    @GET
    @Path("/{id}")
    @Timeout
    @SessionScoped
    public Customer getCustomerById(@PathParam("id") Long id) {
        log.info("CustomerResource.getCustomerById()");

        return customerRepository.findById(id);
    }

    @POST
    @ResponseStatus(201)
    @Timeout(250)
    @Transactional
    public Customer createCustomer(Customer customer) {
        log.info("CustomerResource.createCustomer()");
        log.debug(customer.toString());

        this.customerRepository.persistAndFlush(customer);
        return this.customerRepository.findById(customer.id);
    }

    @PUT
    @Path("/{id}")
    @Timeout(250)
    @Transactional
    public void updateCustomer(@PathParam("id") Long id, Customer customer) {
        log.info("CustomerResource.updateCustomer()");
        log.debug(customer.toString());

        this.customerRepository.update(
                "name = :name, email = :email where id = :id",
                Parameters.with("name", customer.name)
                        .and("email", customer.email)
                        .and("id", id)
        );
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(204)
    @Timeout(250)
    @Transactional
    public void deleteCustomer(@PathParam("id") Long id) {
        log.info("CustomerResource.deleteCustomer()");

        this.customerRepository.deleteById(id);
    }

    @POST
    @Path("/{customerId}/orders")
    @ResponseStatus(201)
    @Timeout(250)
    @Transactional
    public void addOrder(@PathParam("customerId") Long customerId, @Valid Order order) {
        log.info("CustomerResource.addOrder()");
        log.debug(order.toString());

        Customer customer = this.customerRepository.findById(customerId);

        orderRepository.persistAndFlush(order);

        customer.orders.add(order);
    }

    @DELETE
    @Path("/{customerId}/orders/{orderId}")
    @ResponseStatus(204)
    @Timeout(250)
    @Transactional
    public void deleteOrder(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId) {
        log.info("CustomerResource.deleteOrder()");

        Customer customer = this.customerRepository.findById(customerId);
        Order order = customer.orders.stream().filter(i -> i.id == orderId).findAny().get();
        customer.orders.remove(order);
    }

}
