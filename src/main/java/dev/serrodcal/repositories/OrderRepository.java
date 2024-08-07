package dev.serrodcal.repositories;

import dev.serrodcal.dbos.OrderDBO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface OrderRepository extends PanacheRepository<OrderDBO> {

}
