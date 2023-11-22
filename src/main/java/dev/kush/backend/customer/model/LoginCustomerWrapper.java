package dev.kush.backend.customer.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCustomerWrapper {
    @Email(message = "Invalid email address")
    private String email;
    private String password;
}
