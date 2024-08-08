package dev.serrodcal.orders.infrastructure;

import dev.serrodcal.orders.domain.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostgreSQLOrderRepository implements OrderRepository {
}
