package dev.kush.backend.customer.service;

import dev.kush.backend.customer.model.LoginCustomerWrapper;
import dev.kush.backend.customer.model.SendDetailWrapperWithJwt;
import dev.kush.backend.customer.model.SignUpDetailWrapper;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<SendDetailWrapperWithJwt> login(LoginCustomerWrapper loginCustomer);

    ResponseEntity<String> create(SignUpDetailWrapper signUpDetailWrapper);

    ResponseEntity<String> deleteCustomer(LoginCustomerWrapper loginCustomerWrapper);
}
