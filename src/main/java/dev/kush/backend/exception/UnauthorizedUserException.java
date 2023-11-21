package dev.kush.backend.exception;

public class UnauthorizedUserException extends RuntimeException {
    private String message;

    public UnauthorizedUserException(String message) {
        super(message);
    }

    public UnauthorizedUserException() {
        super("User is not authorized");
    }
}
