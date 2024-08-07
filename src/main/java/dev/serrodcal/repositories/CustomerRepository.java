package dev.serrodcal.repositories;

import dev.serrodcal.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface CustomerRepository extends PanacheRepository<Customer> {

}
