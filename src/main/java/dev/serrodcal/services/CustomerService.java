package dev.serrodcal.services;

import dev.serrodcal.entities.Customer;
import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.CustomerRepository;
import dev.serrodcal.services.dtos.CustomerDTO;
import dev.serrodcal.services.dtos.NewCustomerCommand;
import dev.serrodcal.services.dtos.OrderDTO;
import dev.serrodcal.services.dtos.UpdateCustomerCommand;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    OrderService orderService;

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public List<CustomerDTO> getAll() {
        List<CustomerDTO> customers = this.customerRepository.listAll().stream()
                .map(i -> new CustomerDTO(
                        i.id,
                        i.name,
                        i.email,
                        i.orders.stream().map(j -> new OrderDTO(j.id, j.product, j.quantity, j.metadata.createdAt, j.metadata.updatedAt)).toList(),
                        i.metadata.createdAt,
                        i.metadata.updatedAt
                ))
                .toList();
        return customers;
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public CustomerDTO getById(Long id) {
        Customer customer = customerRepository.findById(id);
        return new CustomerDTO(
                customer.id,
                customer.name,
                customer.name,
                customer.orders.stream().map(i -> new OrderDTO(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt)).toList(),
                customer.metadata.createdAt,
                customer.metadata.updatedAt
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public CustomerDTO save(NewCustomerCommand newCustomerCommand) {
        Customer customer = new Customer();
        customer.name = newCustomerCommand.name();
        customer.email = newCustomerCommand.email();

        this.customerRepository.persistAndFlush(customer);
        Customer result = this.customerRepository.findById(customer.id);

        return new CustomerDTO(
                result.id,
                result.name,
                result.name,
                result.orders.stream().map(i -> new OrderDTO(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt)).toList(),
                result.metadata.createdAt,
                result.metadata.updatedAt
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void update(Long id, UpdateCustomerCommand updateCustomerCommand) {
        this.customerRepository.update(
                "name = :name, email = :email where id = :id",
                Parameters.with("name", updateCustomerCommand.name())
                        .and("email", updateCustomerCommand.email())
                        .and("id", id)
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void deleteById(Long id) {
        this.customerRepository.deleteById(id);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void addOrder(Long customerId, Order order) {
        Customer customer = this.customerRepository.findById(customerId);

        this.orderService.save(order);
        customer.orders.add(order);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void deleteOrder(Long customerId, Long orderId) {
        Customer customer = this.customerRepository.findById(customerId);

        if (Objects.isNull(customer) || Objects.isNull(customer.orders) || customer.orders.isEmpty())
            throw new NoSuchElementException("No orders for selected customer");

        Order order = customer.orders.stream().filter(i -> i.id == orderId).findAny().get();
        customer.orders.remove(order);
    }
}
