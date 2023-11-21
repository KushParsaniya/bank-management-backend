package dev.kush.backend.customer.service;

import dev.kush.backend.frontendDetail.model.LoginCustomerWrapper;
import dev.kush.backend.frontendDetail.model.SendDetailWrapper;
import dev.kush.backend.frontendDetail.model.SignUpDetailWrapper;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<SendDetailWrapper> login(LoginCustomerWrapper loginCustomer);

    ResponseEntity<String> create(SignUpDetailWrapper signUpDetailWrapper);

    ResponseEntity<String> deleteCustomer(LoginCustomerWrapper loginCustomerWrapper);
}
