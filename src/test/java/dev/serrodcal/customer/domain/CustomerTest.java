package dev.serrodcal.customer.domain;

import static org.junit.jupiter.api.Assertions.*;

import dev.serrodcal.customers.domain.Customer;
import dev.serrodcal.orders.domain.Order;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() throws IllegalAccessException {
        List<Order> orders = List.of(
                new Order(1L, "phone", 2, null, null),
                new Order(2L, "laptop", 1, null, null),
                new Order(3L, "pocketCharger", 1, null, null)
                );

        this.customer = new Customer(1L, "John", "john@email.com", orders, null, null);
    }

    // Happy path

    @Test
    public void updateCustomerName() throws IllegalAccessException {
        // Given
        String newName = "David";

        // When
        this.customer.updateName(newName);

        //Then
        assertEquals(this.customer.getName(), newName);
    }

    @Test
    public void updateCustomerEmail() throws IllegalAccessException {
        // Given
        String newEmail = "john@newEmail.com";

        // When
        this.customer.updateEmail(newEmail);

        //Then
        assertEquals(this.customer.getEmail(), newEmail);
    }

    @Test
    public void addNonExistingOrder() throws IllegalAccessException {
        // Given
        Order newOrder = new Order(4L, "oranges", 1, null, null);

        // When
        this.customer.addOrder(newOrder);

        //Then
        assertTrue(this.customer.getOrders().size() == 4);
        assertTrue(this.customer.getOrders().contains(newOrder));
    }

    @Test
    public void removeExistingOrder() throws IllegalAccessException {
        // Given
        Order existingOrder = new Order(3L, "pocketCharger", 1, null, null);

        // When
        this.customer.removeOrder(existingOrder);

        //Then
        assertTrue(this.customer.getOrders().size() == 2);
        assertTrue(!this.customer.getOrders().contains(existingOrder));
    }

    @Test
    public void updateOrder() throws IllegalAccessException {
        // Given
        Order orderToUpdate = new Order(3L, "pocketCharger", 3, null, null);

        // When
        this.customer.updateOrder(orderToUpdate);

        //Then
        assertTrue(this.customer.getOrders().contains(orderToUpdate));
        assertTrue(this.customer.getOrders().getLast().equals(orderToUpdate));
    }

}
