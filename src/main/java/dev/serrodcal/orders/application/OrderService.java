package dev.serrodcal.orders.application;

import dev.serrodcal.orders.domain.Order;
import dev.serrodcal.orders.domain.OrderRepository;
import dev.serrodcal.shared.infrastructure.dtos.PaginatedQuery;
import dev.serrodcal.orders.application.dtos.OrderDTO;
import dev.serrodcal.shared.application.dtos.Metadata;
import dev.serrodcal.shared.application.dtos.PaginatedDTO;
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
        List<OrderDTO> orders = this.orderRepository.getAll(query.page(), query.size()).stream()
                .map(i -> new OrderDTO(i.getId(), i.getProduct(), i.getQuantity(), i.getCreatedAt(), i.getUpdatedAt()))
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
        Order order = new Order(id, "someProduct", 1, null, null);
        Order result = this.orderRepository.getById(order);

        if (Objects.isNull(result))
            throw new NoSuchElementException("Order id does not exist");

        return new OrderDTO(
                result.getId(),
                result.getProduct(),
                result.getQuantity(),
                result.getCreatedAt(),
                result.getUpdatedAt()
        );
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void update(Long id, OrderDTO orderDTO) {
        Order order = new Order(id, orderDTO.product(), orderDTO.quantity(), null, null);

        this.orderRepository.updateOrder(order);
    }


}
