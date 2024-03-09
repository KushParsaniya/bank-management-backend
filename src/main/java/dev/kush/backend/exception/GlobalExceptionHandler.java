package dev.kush.backend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                NOT_FOUND
        );
        return new ResponseEntity<>(errorDetails, NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                BAD_REQUEST
        );
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public final ResponseEntity<Object> handleUnprocessableEntityException(UnprocessableEntityException ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                UNPROCESSABLE_ENTITY
        );
        return new ResponseEntity<>(errorDetails, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(CONFLICT)
    public final ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                CONFLICT
        );
        return new ResponseEntity<>(errorDetails, CONFLICT);
    }


    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(UNAUTHORIZED)
    public final ResponseEntity<Object> handleUnauthorizedUserException(UnauthorizedUserException ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                UNAUTHORIZED
        );
        return new ResponseEntity<>(errorDetails, UNAUTHORIZED);
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public final ResponseEntity<Object> handleFileNotFound(FileNotFoundException ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                NOT_FOUND
        );
        return new ResponseEntity<>(errorDetails, NOT_FOUND);
    }
}
