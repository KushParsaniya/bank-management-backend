package dev.kush.backend.exception;

public class UnprocessableEntityException extends RuntimeException {
    private String message;

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException() {
        super("unprocessable entity");
    }
}
