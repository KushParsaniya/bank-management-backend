package dev.kush.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;
}
