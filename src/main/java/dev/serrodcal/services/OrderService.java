package dev.serrodcal.services;

import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.OrderRepository;
import dev.serrodcal.services.dtos.OrderDTO;
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
    public List<OrderDTO> getAll() {
        return orderRepository.listAll().stream()
                .map(i -> new OrderDTO(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt))
                .toList();
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public OrderDTO getById(Long id) {
        Order order = orderRepository.findById(id);
        return new OrderDTO(
                order.id,
                order.product,
                order.quantity,
                order.metadata.createdAt,
                order.metadata.updatedAt
        );
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
