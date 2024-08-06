package dev.serrodcal.resources;

import dev.serrodcal.entities.Customer;
import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.CustomerRepository;
import dev.serrodcal.repositories.OrderRepository;
import dev.serrodcal.resources.dtos.requests.AddOrderRequest;
import dev.serrodcal.resources.dtos.requests.NewCustomerRequest;
import dev.serrodcal.resources.dtos.requests.UpdateCustomerRequest;
import dev.serrodcal.resources.dtos.responses.CustomerResponse;
import dev.serrodcal.resources.dtos.responses.OrderResponse;
import dev.serrodcal.services.CustomerService;
import dev.serrodcal.services.dtos.*;
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
    public List<CustomerResponse> getAllCustomers() {
        log.info("CustomerResource.getAllCustomers()");

        List<CustomerResponse> customers = this.customerService.getAll().stream()
                .map(i -> new CustomerResponse(
                        i.id(),
                        i.name(),
                        i.email(),
                        i.orders().stream().map(j -> new OrderResponse(j.id(), j.product(), j.quantity(), j.createdAt(), j.updatedAt())).toList(),
                        i.createdAt(),
                        i.updatedAt()
                ))
                .toList();

        return customers;
    }

    @GET
    @Path("/{id}")
    @Timeout
    public CustomerResponse getCustomerById(@PathParam("id") Long id) {
        log.info("CustomerResource.getCustomerById()");

        CustomerDTO customerDTO = customerService.getById(id);

        return new CustomerResponse(
                customerDTO.id(),
                customerDTO.name(),
                customerDTO.email(),
                customerDTO.orders().stream().map(i -> new OrderResponse(i.id(), i.product(), i.quantity(), i.createdAt(), i.updatedAt())).toList(),
                customerDTO.createdAt(),
                customerDTO.updatedAt()
        );
    }

    @POST
    @ResponseStatus(201)
    @Timeout(250)
    public CustomerResponse createCustomer(NewCustomerRequest newCustomerRequest) {
        log.info("CustomerResource.createCustomer()");
        log.debug(newCustomerRequest.toString());

        CustomerDTO customerDTO = this.customerService.save(new NewCustomerCommand(
                newCustomerRequest.name(),
                newCustomerRequest.email()
        ));

        return new CustomerResponse(
                customerDTO.id(),
                customerDTO.name(),
                customerDTO.email(),
                customerDTO.orders().stream().map(i -> new OrderResponse(i.id(), i.product(), i.quantity(), i.createdAt(), i.updatedAt())).toList(),
                customerDTO.createdAt(),
                customerDTO.updatedAt()
        );
    }

    @PUT
    @Path("/{id}")
    @Timeout(250)
    public void updateCustomer(@PathParam("id") Long id, UpdateCustomerRequest updateCustomerRequest) {
        log.info("CustomerResource.updateCustomer()");
        log.debug(updateCustomerRequest.toString());

        this.customerService.update(id, new UpdateCustomerCommand(
                updateCustomerRequest.name(),
                updateCustomerRequest.email()
        ));
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
    public void addOrder(@PathParam("customerId") Long customerId, @Valid AddOrderRequest addOrderRequest) {
        log.info("CustomerResource.addOrder()");
        log.debug(addOrderRequest.toString());

        AddOrderCommand addOrderCommand = new AddOrderCommand(
                addOrderRequest.product(),
                addOrderRequest.quantity()
        );

        this.customerService.addOrder(customerId, addOrderCommand);
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
