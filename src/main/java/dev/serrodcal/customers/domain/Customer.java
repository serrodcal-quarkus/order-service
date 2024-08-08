package dev.serrodcal.customers.domain;

import dev.serrodcal.customers.domain.expcetions.ProductAlreadyExistsException;
import dev.serrodcal.customers.domain.vo.CustomerEmail;
import dev.serrodcal.customers.domain.vo.CustomerName;
import dev.serrodcal.orders.domain.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {

    private Long id;
    private CustomerName name;
    private CustomerEmail email;
    private List<Order> orders;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(Long id, String name, String email, List<Order> orders) {
        this.id = id;
        this.name = new CustomerName(name);
        this.email = new CustomerEmail(email);
        this.orders = new ArrayList<>(orders);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateName(String name) throws IllegalAccessException {
        this.name.update(name);
    }

    public void updateEmail(String email) throws IllegalAccessException {
        this.email.update(email);
    }

    public void addOrder(Order order) {
        Objects.requireNonNull(order, "Order to add cannot be null");

        if (this.orders.contains(order))
            throw new ProductAlreadyExistsException("The order product already exists");

        this.orders.add(order);
    }

    public void removeOrder(Order order) {
        Objects.requireNonNull(order, "Order to remove cannot be null");

        this.orders.removeIf(i -> i.equals(order));
    }

    public void updateOrder(Order order) {
        Objects.requireNonNull(order, "Order to update cannot be null");

        if (this.orders.removeIf(i -> i.equals(order)))
            this.orders.add(order);
    }

}
