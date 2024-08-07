package dev.serrodcal.domain.vos;

import java.util.Objects;

public class ProductName {

    private String product;

    public ProductName(String product) throws IllegalAccessException {
        if (Objects.isNull(product) || product.isBlank())
            throw new IllegalAccessException("Invalid product name");

        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void update(String product) throws IllegalAccessException {
        if (Objects.isNull(product) || product.isBlank())
            throw new IllegalAccessException("Invalid product name");

        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product);
    }
}
