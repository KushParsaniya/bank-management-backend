package dev.kush.backend.customer.service;

import dev.kush.backend.customer.model.LoginCustomerWrapper;
import dev.kush.backend.customer.model.SendDetailWrapper;
import dev.kush.backend.customer.model.SignUpDetailWrapper;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<SendDetailWrapper> login(LoginCustomerWrapper loginCustomer);

    ResponseEntity<String> create(SignUpDetailWrapper signUpDetailWrapper);

    ResponseEntity<String> deleteCustomer(LoginCustomerWrapper loginCustomerWrapper);
}
