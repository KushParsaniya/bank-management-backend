package dev.kush.backend.backend.frontendDetail.model;

import dev.kush.backend.backend.account.models.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDetailWrapper {
    private String username;
    private String email;
    private String password;
    private AccountType accountType;

}
