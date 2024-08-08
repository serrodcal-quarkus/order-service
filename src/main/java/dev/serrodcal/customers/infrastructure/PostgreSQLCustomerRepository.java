package dev.serrodcal.customers.infrastructure;

import dev.serrodcal.customers.application.dtos.CustomerDTO;
import dev.serrodcal.customers.domain.Customer;
import dev.serrodcal.customers.domain.CustomerRepository;
import dev.serrodcal.orders.application.dtos.OrderDTO;
import dev.serrodcal.orders.domain.Order;
import dev.serrodcal.orders.infrastructure.OrderDBO;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@ApplicationScoped
public class PostgreSQLCustomerRepository implements CustomerRepository {

    @Override
    public List<Customer> getAll(Integer page, Integer size) throws IllegalArgumentException {
        return findAll().page(Page.of(page, size)).list().stream()
                .map(i -> {
                    try {
                        return new Customer(
                                i.id,
                                i.name,
                                i.email,
                                i.orderDBOS.stream().map(j -> {
                                    try {
                                        return new Order(j.id, j.product, j.quantity, j.metadata.createdAt, j.metadata.updatedAt);
                                    } catch (IllegalArgumentException e) {
                                        throw new IllegalArgumentException(e);
                                    }
                                }).toList(),
                                i.metadata.createdAt,
                                i.metadata.updatedAt
                        );
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(e);
                    }
                })
                .toList();
    }

    @Override
    public Customer getById(Customer customer) {
        Objects.requireNonNull(customer, "Customer cannot be null");

        if (Objects.isNull(customer.getId()))
            throw new IllegalArgumentException("Customer id cannot be null");

        CustomerDBO dbo = findById(customer.getId());

        if(Objects.isNull(dbo))
            throw new NoSuchElementException("Customer id does not exist");

        return new Customer(
                dbo.id,
                dbo.name,
                dbo.email,
                dbo.orderDBOS.stream().map(i -> new Order(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt)).toList(),
                dbo.metadata.createdAt,
                dbo.metadata.updatedAt
        );
    }

    @Override
    public Customer createCustomer(Customer customer) throws IllegalArgumentException {
        Objects.requireNonNull(customer, "Customer cannot be null");

        CustomerDBO dbo = new CustomerDBO();
        dbo.name = customer.getName();
        dbo.email = customer.getEmail();
        dbo.orderDBOS = new ArrayList<>();
        dbo.persistAndFlush();

        return new Customer(
                dbo.id,
                dbo.name,
                dbo.email,
                new ArrayList<>(),
                dbo.metadata.createdAt,
                dbo.metadata.updatedAt
        );
    }

    @Override
    public void updateCustomer(Customer customer) {
        Objects.requireNonNull(customer, "Customer cannot be null");

        update(
                "name = :name, email = :email where id = :id",
                Parameters.with("name", customer.getName())
                        .and("email", customer.getEmail())
                        .and("id", customer.getId())
        );
    }

    @Override
    public void deleteCustomer(Customer customer) {
        Objects.requireNonNull(customer, "Customer cannot be null");

        if (Objects.isNull(customer.getId()))
            throw new IllegalArgumentException("Customer id is null");

        deleteById(customer.getId());
    }

    @Override
    public void addOrder(Customer customer, Order order) {
        Objects.requireNonNull(customer, "Customer cannot be null");
        Objects.requireNonNull(order, "Order cannot be null");

        CustomerDBO customerDBO = findById(customer.getId());

        if (Objects.isNull(customerDBO))
            throw new NoSuchElementException("Does not exist customer");

        OrderDBO orderDBO = new OrderDBO();
        orderDBO.product = order.getProduct();
        orderDBO.quantity = order.getQuantity();
        orderDBO.persistAndFlush();

        customerDBO.orderDBOS.add(orderDBO);
    }

    @Override
    public void removeOrder(Customer customer, Order order) {
        Objects.requireNonNull(customer, "Customer cannot be null");
        Objects.requireNonNull(order, "Order cannot be null");

        CustomerDBO customerDBO = findById(customer.getId());

        if (Objects.isNull(customerDBO) || Objects.isNull(customerDBO.orderDBOS) || customerDBO.orderDBOS.isEmpty())
            throw new NoSuchElementException("No orders for selected customer");

        OrderDBO orderDBO = customerDBO.orderDBOS.stream().filter(i -> i.id == order.getId()).findAny().get();
        customerDBO.orderDBOS.remove(orderDBO);
    }

}
