package org.example.core.exceptions;

public class WrongCypherTypeException extends RuntimeException {
    public WrongCypherTypeException(String message) {
        super(message);
    }
}
