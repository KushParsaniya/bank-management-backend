package dev.kush.backend.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException() {
        super("file not found.");
    }
}
