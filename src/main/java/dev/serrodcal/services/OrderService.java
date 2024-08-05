package dev.serrodcal.services;

import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.OrderRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public List<Order> getAll() {
        return orderRepository.listAll();
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public Order getById(Long id) {
        return orderRepository.findById(id);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void save(Order order) {
        this.orderRepository.persistAndFlush(order);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void update(Long id, Order order) {
        this.orderRepository.update(
                "product = :product, quantity = :quantity where id = :id",
                Parameters.with("product", order.product)
                        .and("quantity", order.quantity)
                        .and("id", id)
        );
    }


}
