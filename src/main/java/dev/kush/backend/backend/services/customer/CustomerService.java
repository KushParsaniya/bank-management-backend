package dev.kush.backend.backend.services.customer;

import dev.kush.backend.backend.models.wrapper.LoginCustomerWrapper;
import dev.kush.backend.backend.models.wrapper.SendDetailWrapper;
import dev.kush.backend.backend.models.wrapper.SignUpDetailWrapper;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<SendDetailWrapper> login(LoginCustomerWrapper loginCustomer);

    ResponseEntity<String> create(SignUpDetailWrapper signUpDetailWrapper);
}
