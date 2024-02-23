package dev.kush.backend.customer.model;

import dev.kush.backend.account.models.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDetailWrapper {
    @Size(min = 2 , message = "username should be at least 2 characters.")
    private String username;
    @Email(message = "Invalid email address")
    private String email;
    @Size(min = 4 , message = "password should be at least 4 characters.")
    private String password;
    private AccountType accountType;

}
