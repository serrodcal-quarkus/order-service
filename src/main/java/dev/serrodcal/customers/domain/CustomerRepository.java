package dev.serrodcal.customers.domain;

import dev.serrodcal.customers.infrastructure.CustomerDBO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface CustomerRepository extends PanacheRepository<CustomerDBO> {

}
