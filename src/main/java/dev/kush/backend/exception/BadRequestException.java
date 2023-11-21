package dev.kush.backend.exception;

public class BadRequestException extends RuntimeException {

    private  String message;
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super("Bad request");
    }
}
