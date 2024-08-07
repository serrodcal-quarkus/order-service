package dev.serrodcal.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException() {
        super();
    }

    public ProductAlreadyExistsException(String msg) {
        super(msg);
    }

}
