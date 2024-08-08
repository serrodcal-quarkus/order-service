package dev.serrodcal.customers.infrastructure;

import dev.serrodcal.customers.domain.Customer;
import dev.serrodcal.customers.domain.CustomerRepository;
import dev.serrodcal.orders.domain.Order;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PostgreSQLCustomerRepository implements CustomerRepository {

    @Override
    public List<Customer> getAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public List<Customer> getById(Customer customer, Integer page, Integer size) {
        return List.of();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(Customer customer) {

    }

    @Override
    public void addOrder(Customer customer, Order order) {

    }

    @Override
    public void removeOrder(Customer customer, Order order) {

    }

}
