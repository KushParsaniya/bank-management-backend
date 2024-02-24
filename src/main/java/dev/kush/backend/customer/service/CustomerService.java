package dev.kush.backend.customer.service;

import dev.kush.backend.customer.dto.LoginCustomerDto;
import dev.kush.backend.customer.dto.SendDetailDtoWithJwt;
import dev.kush.backend.customer.dto.SignUpDetailDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<SendDetailDtoWithJwt> login(LoginCustomerDto loginCustomer);

    ResponseEntity<String> create(SignUpDetailDto signUpDetailDto);

    ResponseEntity<String> deleteCustomer(Long customerId);

    void enableAccount(String email);

    String confirmToken(String token);
}
