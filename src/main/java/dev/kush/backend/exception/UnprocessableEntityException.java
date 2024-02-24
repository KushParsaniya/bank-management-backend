package dev.kush.backend.exception;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException() {
        super("unprocessable entity");
    }
}
