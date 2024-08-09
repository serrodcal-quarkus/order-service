package dev.serrodcal.orders.infrastructure;

import dev.serrodcal.orders.domain.Order;
import dev.serrodcal.orders.domain.OrderRepository;
import dev.serrodcal.orders.shared.mappers.OrderMapper;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@ApplicationScoped
public class PostgreSQLOrderRepository implements OrderRepository {

    @Inject
    OrderMapper mapper;

    @Override
    public List<Order> getAll(Integer page, Integer size) {
        return findAll().page(Page.of(page, size)).list().stream()
                .map(i -> this.mapper.mapOrderDBOToOrder(i))
                .toList();
    }

    @Override
    public Order getById(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");

        if (Objects.isNull(order.getId()))
            throw new IllegalArgumentException("Order id cannot be null");

        OrderDBO dbo = findById(order.getId());

        if(Objects.isNull(dbo))
            throw new NoSuchElementException("Order id does not exist");

        return this.mapper.mapOrderDBOToOrder(dbo);
    }

    @Override
    public void updateOrder(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");

        OrderDBO dbo = findById(order.getId());
        if (Objects.isNull(dbo))
            throw new NoSuchElementException("There are no order for that id");

        update(
                "product = :product, quantity = :quantity where id = :id",
                Parameters.with("product", order.getProduct())
                        .and("quantity", order.getQuantity())
                        .and("id", order.getId())
        );
    }

}
