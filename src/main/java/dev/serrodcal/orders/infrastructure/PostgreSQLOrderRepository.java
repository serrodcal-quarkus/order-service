package dev.serrodcal.orders.infrastructure;

import dev.serrodcal.orders.domain.Order;
import dev.serrodcal.orders.domain.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PostgreSQLOrderRepository implements OrderRepository {

    @Override
    public List<Order> getAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public List<Order> getById(Order order, Integer page, Integer size) {
        return List.of();
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public void updateOrder(Order order) {

    }

    @Override
    public void deleteOrder(Order order) {

    }

}
