package dev.serrodcal.order.domain;

import dev.serrodcal.orders.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setUp() throws IllegalAccessException {
        this.order = new Order(1L, "iPhone 13", 1, null, null);
    }

    @Test
    public void updateProductName() throws IllegalAccessException {
        // Given
        String newProduct = "Google Pixel 7";

        // When
        this.order.updateProduct(newProduct);

        //Then
        assertEquals(this.order.getProduct(), newProduct);
    }

    @Test
    public void updateQuantity() throws IllegalAccessException {
        // Given
        Integer newQuantity = 3;

        // When
        this.order.updateQuantity(3);

        //Then
        assertEquals(this.order.getQuantity(), newQuantity);
    }

}
