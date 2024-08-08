package dev.serrodcal.customers.infrastructure;

import dev.serrodcal.customers.domain.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostgreSQLCustomerRepository implements CustomerRepository {
}
