package dev.serrodcal.customers.application;

import dev.serrodcal.customers.application.dtos.AddOrderCommand;
import dev.serrodcal.customers.application.dtos.CustomerDTO;
import dev.serrodcal.customers.application.dtos.NewCustomerCommand;
import dev.serrodcal.customers.application.dtos.UpdateCustomerCommand;
import dev.serrodcal.customers.domain.Customer;
import dev.serrodcal.orders.application.dtos.OrderDTO;
import dev.serrodcal.orders.domain.Order;
import dev.serrodcal.customers.domain.CustomerRepository;
import dev.serrodcal.shared.infrastructure.dtos.PaginatedQuery;
import dev.serrodcal.shared.application.dtos.Metadata;
import dev.serrodcal.shared.application.dtos.PaginatedDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public PaginatedDTO<List<CustomerDTO>> getAll(PaginatedQuery query) {
        List<CustomerDTO> customers = this.customerRepository.getAll(query.page(), query.size()).stream()
                .map(i -> new CustomerDTO(
                        i.getId(),
                        i.getName(),
                        i.getEmail(),
                        i.getOrders().stream().map(j -> new OrderDTO(j.getId(), j.getProduct(), j.getQuantity(), j.getCreatedAt(), j.getUpdatedAt())).toList(),
                        i.getCreatedAt(),
                        i.getUpdatedAt()
                ))
                .toList();
        return new PaginatedDTO<>(
                customers,
                new Metadata(
                        query.page(),
                        query.size(),
                        (int) this.customerRepository.count()
                )
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public CustomerDTO getById(Long id) {
        Customer customer = new Customer(id, "someName", "email@email.com", new ArrayList<>(), null, null);
        Customer result = this.customerRepository.getById(customer);

        return new CustomerDTO(
                result.getId(),
                result.getName(),
                result.getEmail(),
                result.getOrders().stream().map(i -> new OrderDTO(i.getId(), i.getProduct(), i.getQuantity(), i.getCreatedAt(), i.getUpdatedAt())).toList(),
                result.getCreatedAt(),
                result.getUpdatedAt()
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public CustomerDTO save(NewCustomerCommand newCustomerCommand) throws IllegalAccessException {
        Customer customer = new Customer(null, newCustomerCommand.name(), newCustomerCommand.email(), new ArrayList<>(), null, null);
        Customer result = this.customerRepository.createCustomer(customer);

        return new CustomerDTO(
                result.getId(),
                result.getName(),
                result.getEmail(),
                result.getOrders().stream().map(i -> new OrderDTO(i.getId(), i.getProduct(), i.getQuantity(), i.getCreatedAt(), i.getUpdatedAt())).toList(),
                result.getCreatedAt(),
                result.getUpdatedAt()
        );
//        CustomerDBO customerDBO = new CustomerDBO();
//        customerDBO.name = newCustomerCommand.name();
//        customerDBO.email = newCustomerCommand.email();
//
//        this.customerRepository.persistAndFlush(customerDBO);
//        CustomerDBO result = this.customerRepository.findById(customerDBO.id);
//
//        return new CustomerDTO(
//                result.id,
//                result.name,
//                result.email,
//                result.orderDBOS.stream().map(i -> new OrderDTO(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt)).toList(),
//                result.metadata.createdAt,
//                result.metadata.updatedAt
//        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void update(Long id, UpdateCustomerCommand updateCustomerCommand) {
        Customer customer = new Customer(id, updateCustomerCommand.name(), updateCustomerCommand.email(), new ArrayList<>(), null, null);

        this.customerRepository.updateCustomer(customer);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void deleteById(Long id) {
        Customer customer = new Customer(id, "someName", "email@email.com", new ArrayList<>(), null, null);
        this.customerRepository.deleteCustomer(customer);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void addOrder(Long customerId, AddOrderCommand addOrderCommand) {
        Customer customer = new Customer(customerId, "someName", "email@email.com", new ArrayList<>(), null, null);
        Order order = new Order(null, addOrderCommand.product(), addOrderCommand.quantity(), null, null);

        this.customerRepository.addOrder(customer, order);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void deleteOrder(Long customerId, Long orderId) {
        Customer customer = new Customer(customerId, "someName", "email@email.com", new ArrayList<>(), null, null);
        Order order = new Order(orderId, "someProduct", 1, null, null);

        this.customerRepository.removeOrder(customer, order);
    }
}
