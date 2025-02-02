package dev.serrodcal.orders.domain.vo;

import java.util.Objects;

public class ProductQuantity {

    private Integer quantity;

    public ProductQuantity(Integer quantity) throws IllegalArgumentException {
        Objects.requireNonNull(quantity, "Quantity cannot be null");

        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be less than zero");

        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void update(Integer quantity) throws IllegalArgumentException {
        Objects.requireNonNull(quantity, "Cannot update because increment is null");

        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be less than zero");

        this.quantity = quantity;
    }

}
