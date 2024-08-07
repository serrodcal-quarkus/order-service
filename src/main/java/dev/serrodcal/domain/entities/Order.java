package dev.serrodcal.domain.entities;

import dev.serrodcal.domain.vos.ProductName;
import dev.serrodcal.domain.vos.ProductQuantity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private Long id;
    private ProductName product;
    private ProductQuantity quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(Long id, String product, Integer quantity) throws IllegalAccessException {
        this.id = id;
        this.product = new ProductName(product);
        this.quantity = new ProductQuantity(quantity);
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product.getProduct();
    }

    public Integer getQuantity() {
        return this.quantity.getQuantity();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateProduct(String product) throws IllegalAccessException {
        this.product.update(product);
    }

    public void updateQuantity(Integer quantity) throws IllegalAccessException {
        this.quantity.update(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(product, order.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, createdAt, updatedAt);
    }
}
