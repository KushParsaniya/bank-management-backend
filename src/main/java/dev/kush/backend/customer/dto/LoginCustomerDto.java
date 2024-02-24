package dev.kush.backend.customer.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCustomerDto {
    @Email(message = "Invalid email address")
    private String email;
    private String password;
}
