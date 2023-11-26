package dev.kush.backend.exception;

public class ConflictException extends RuntimeException {
    private String message;

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException() {
        super("conflict occurred");
    }
}
