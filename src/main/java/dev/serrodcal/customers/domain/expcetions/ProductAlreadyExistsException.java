package dev.serrodcal.customers.domain.expcetions;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException() {
        super();
    }

    public ProductAlreadyExistsException(String msg) {
        super(msg);
    }

}
