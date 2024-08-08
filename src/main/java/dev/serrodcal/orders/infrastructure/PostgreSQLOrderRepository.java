package dev.serrodcal.orders.infrastructure;

import dev.serrodcal.customers.domain.Customer;
import dev.serrodcal.customers.infrastructure.CustomerDBO;
import dev.serrodcal.orders.domain.Order;
import dev.serrodcal.orders.domain.OrderRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@ApplicationScoped
public class PostgreSQLOrderRepository implements OrderRepository {

    @Override
    public List<Order> getAll(Integer page, Integer size) {
        return findAll().page(Page.of(page, size)).list().stream()
                .map(i -> new Order(i.id, i.product, i.quantity, i.metadata.createdAt, i.metadata.updatedAt))
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

        return new Order(
                dbo.id,
                dbo.product,
                dbo.quantity,
                dbo.metadata.createdAt,
                dbo.metadata.updatedAt
        );
    }

    @Override
    public void updateOrder(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");

        update(
                "product = :product, quantity = :quantity where id = :id",
                Parameters.with("product", order.getProduct())
                        .and("quantity", order.getQuantity())
                        .and("id", order.getId())
        );
    }

}
