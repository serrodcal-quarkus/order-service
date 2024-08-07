package dev.serrodcal.domain.vos;

import java.util.Objects;

public class CustomerName {

    private String name;

    public CustomerName(String name) {
        if (Objects.isNull(name) || name.isBlank())
            throw new IllegalArgumentException("Customer name cannot be null or blank");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void update(String name) throws IllegalAccessException {
        if (Objects.isNull(name) || name.isBlank())
            throw new IllegalAccessException("Invalid customer name");

        this.name = name;
    }

}
