package dev.serrodcal.orders.application;

import dev.serrodcal.orders.domain.Order;
import dev.serrodcal.orders.domain.OrderRepository;
import dev.serrodcal.orders.shared.mappers.OrderMapper;
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

    @Inject
    OrderMapper mapper;

    @CircuitBreaker(requestVolumeThreshold = 4)
    @SessionScoped
    public PaginatedDTO<List<OrderDTO>> getAll(PaginatedQuery query) {
        List<OrderDTO> orders = this.orderRepository.getAll(query.page(), query.size()).stream()
                .map(i -> this.mapper.mapOrderToOrderDTO(i))
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
        Order order = Order.of(id);
        order = this.orderRepository.getById(order);

        if (Objects.isNull(order))
            throw new NoSuchElementException("Order id does not exist");

        return this.mapper.mapOrderToOrderDTO(order);
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @Transactional
    public void update(Long id, OrderDTO orderDTO) {
        Order order = this.mapper.mapOrderDTOToOrder(orderDTO);
        order.setId(id);

        this.orderRepository.updateOrder(order);
    }


}
