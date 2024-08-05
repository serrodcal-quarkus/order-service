package dev.serrodcal.services;

import dev.serrodcal.entities.Customer;
import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.CustomerRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    OrderService orderService;

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public List<Customer> getAll() {
        return this.customerRepository.listAll();
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public Customer getById(Long id) {
        return customerRepository.findById(id);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void save(Customer customer) {
        this.customerRepository.persistAndFlush(customer);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void update(Long id, Customer customer) {
        this.customerRepository.update(
                "name = :name, email = :email where id = :id",
                Parameters.with("name", customer.name)
                        .and("email", customer.email)
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
        Order order = customer.orders.stream().filter(i -> i.id == orderId).findAny().get();
        customer.orders.remove(order);
    }
}
