package dev.serrodcal.orders.domain;

import dev.serrodcal.orders.infrastructure.OrderDBO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface OrderRepository extends PanacheRepository<OrderDBO> {

}
