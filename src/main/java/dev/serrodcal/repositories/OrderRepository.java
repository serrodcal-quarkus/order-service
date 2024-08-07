package dev.serrodcal.repositories;

import dev.serrodcal.entities.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface OrderRepository extends PanacheRepository<Order> {

}
