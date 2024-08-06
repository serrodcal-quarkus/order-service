package dev.serrodcal.services;

import dev.serrodcal.entities.Order;
import dev.serrodcal.repositories.OrderRepository;
import dev.serrodcal.resources.dtos.pagination.PaginatedQuery;
import dev.serrodcal.services.dtos.OrderDTO;
import dev.serrodcal.services.dtos.pagination.Metadata;
import dev.serrodcal.services.dtos.pagination.PaginatedDTO;
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
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public PaginatedDTO<List<OrderDTO>> getAll(PaginatedQuery query) {
        List<OrderDTO> orders = orderRepository.findAll().page(Page.of(query.page(), query.size())).list().stream()
                .map(i -> new OrderDTO(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt))
                .toList();
        return new PaginatedDTO<>(
            orders,
                new Metadata(
                        query.page(),
                        query.size(),
                        (int) this.orderRepository.count()
                )
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public OrderDTO getById(Long id) {
        Order order = orderRepository.findById(id);

        if (Objects.isNull(order))
            throw new NoSuchElementException("Order id does not exist");

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
