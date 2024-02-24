package dev.kush.backend.exception;

public class UnauthorizedUserException extends RuntimeException {

    public UnauthorizedUserException(String message) {
        super(message);
    }

    public UnauthorizedUserException() {
        super("User is not authorized");
    }
}
