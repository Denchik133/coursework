package org.example.core.exceptions;

public class WrongCipherTypeException extends RuntimeException {
    public WrongCipherTypeException(String message) {
        super(message);
    }
}
