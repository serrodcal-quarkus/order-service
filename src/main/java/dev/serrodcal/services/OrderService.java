package dev.serrodcal.services;

import dev.serrodcal.dbos.OrderDBO;
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
        OrderDBO orderDBO = orderRepository.findById(id);

        if (Objects.isNull(orderDBO))
            throw new NoSuchElementException("Order id does not exist");

        return new OrderDTO(
                orderDBO.id,
                orderDBO.product,
                orderDBO.quantity,
                orderDBO.metadata.createdAt,
                orderDBO.metadata.updatedAt
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void save(OrderDBO orderDBO) {
        this.orderRepository.persistAndFlush(orderDBO);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void update(Long id, OrderDBO orderDBO) {
        this.orderRepository.update(
                "product = :product, quantity = :quantity where id = :id",
                Parameters.with("product", orderDBO.product)
                        .and("quantity", orderDBO.quantity)
                        .and("id", id)
        );
    }


}
