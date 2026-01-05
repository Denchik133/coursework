package org.example.core.asymmetric.exceptions;

public class MessageIsForAnotherUserException extends Exception {
    public MessageIsForAnotherUserException(String message) {
        super(message);
    }
}
