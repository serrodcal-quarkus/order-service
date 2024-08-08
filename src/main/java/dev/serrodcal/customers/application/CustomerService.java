package dev.serrodcal.customers.application;

import dev.serrodcal.customers.application.dtos.AddOrderCommand;
import dev.serrodcal.customers.application.dtos.CustomerDTO;
import dev.serrodcal.customers.application.dtos.NewCustomerCommand;
import dev.serrodcal.customers.application.dtos.UpdateCustomerCommand;
import dev.serrodcal.customers.infrastructure.CustomerDBO;
import dev.serrodcal.orders.application.dtos.OrderDTO;
import dev.serrodcal.orders.infrastructure.OrderDBO;
import dev.serrodcal.customers.domain.CustomerRepository;
import dev.serrodcal.shared.infrastructure.dtos.PaginatedQuery;
import dev.serrodcal.orders.application.OrderService;
import dev.serrodcal.shared.application.dtos.Metadata;
import dev.serrodcal.shared.application.dtos.PaginatedDTO;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

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
    public PaginatedDTO<List<CustomerDTO>> getAll(PaginatedQuery query) {
        List<CustomerDTO> customers = this.customerRepository.findAll().page(Page.of(query.page(), query.size())).list().stream()
                .map(i -> new CustomerDTO(
                        i.id,
                        i.name,
                        i.email,
                        i.orderDBOS.stream().map(j -> new OrderDTO(j.id, j.product, j.quantity, j.metadata.createdAt, j.metadata.updatedAt)).toList(),
                        i.metadata.createdAt,
                        i.metadata.updatedAt
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
        CustomerDBO customerDBO = customerRepository.findById(id);

        if(Objects.isNull(customerDBO))
            throw new NoSuchElementException("Customer id does not exist");

        return new CustomerDTO(
                customerDBO.id,
                customerDBO.name,
                customerDBO.name,
                customerDBO.orderDBOS.stream().map(i -> new OrderDTO(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt)).toList(),
                customerDBO.metadata.createdAt,
                customerDBO.metadata.updatedAt
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public CustomerDTO save(NewCustomerCommand newCustomerCommand) {
        CustomerDBO customerDBO = new CustomerDBO();
        customerDBO.name = newCustomerCommand.name();
        customerDBO.email = newCustomerCommand.email();

        this.customerRepository.persistAndFlush(customerDBO);
        CustomerDBO result = this.customerRepository.findById(customerDBO.id);

        return new CustomerDTO(
                result.id,
                result.name,
                result.name,
                result.orderDBOS.stream().map(i -> new OrderDTO(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt)).toList(),
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
    public void addOrder(Long customerId, AddOrderCommand addOrderCommand) {
        CustomerDBO customerDBO = this.customerRepository.findById(customerId);

        OrderDBO orderDBO = new OrderDBO();
        orderDBO.product = addOrderCommand.product();
        orderDBO.quantity = addOrderCommand.quantity();

        this.orderService.save(orderDBO);
        customerDBO.orderDBOS.add(orderDBO);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void deleteOrder(Long customerId, Long orderId) {
        CustomerDBO customerDBO = this.customerRepository.findById(customerId);

        if (Objects.isNull(customerDBO) || Objects.isNull(customerDBO.orderDBOS) || customerDBO.orderDBOS.isEmpty())
            throw new NoSuchElementException("No orders for selected customer");

        OrderDBO orderDBO = customerDBO.orderDBOS.stream().filter(i -> i.id == orderId).findAny().get();
        customerDBO.orderDBOS.remove(orderDBO);
    }
}
