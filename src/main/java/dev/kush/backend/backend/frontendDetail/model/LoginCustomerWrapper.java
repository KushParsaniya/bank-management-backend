package dev.kush.backend.backend.frontendDetail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCustomerWrapper {
    private String email;
    private String password;
}
