package dev.serrodcal.customers.domain;

import dev.serrodcal.customers.infrastructure.CustomerDBO;
import dev.serrodcal.orders.domain.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public interface CustomerRepository extends PanacheRepository<CustomerDBO> {

    public List<Customer> getAll(Integer page, Integer size);

    public Customer getById(Customer customer);

    public Customer createCustomer(Customer customer) throws IllegalAccessException;

    public void updateCustomer(Customer customer);

    public void deleteCustomer(Customer customer);

    public void addOrder(Customer customer, Order order);

    public void removeOrder(Customer customer, Order order);

}
