package dev.serrodcal.repositories;

import dev.serrodcal.dbos.CustomerDBO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface CustomerRepository extends PanacheRepository<CustomerDBO> {

}
