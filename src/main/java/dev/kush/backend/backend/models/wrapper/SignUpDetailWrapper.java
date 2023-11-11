package dev.kush.backend.backend.models.wrapper;

import dev.kush.backend.backend.models.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDetailWrapper {
    private String userName;
    private String email;
    private String password;
    private AccountType accountType;

}
