package dev.serrodcal.orders.domain;

import dev.serrodcal.orders.infrastructure.OrderDBO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public interface OrderRepository extends PanacheRepository<OrderDBO> {

    public List<Order> getAll(Integer page, Integer size);

    public Order getById(Order order);

    public Order createOrder(Order order) throws IllegalAccessException;

    public void updateOrder(Order order);

    public void deleteOrder(Order order);

}
