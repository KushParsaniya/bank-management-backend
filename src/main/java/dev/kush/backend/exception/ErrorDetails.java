package dev.kush.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
