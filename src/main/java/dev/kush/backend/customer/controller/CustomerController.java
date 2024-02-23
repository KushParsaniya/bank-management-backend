package dev.kush.backend.customer.controller;

import dev.kush.backend.customer.model.LoginCustomerWrapper;
import dev.kush.backend.customer.model.SendDetailWrapperWithJwt;
import dev.kush.backend.customer.model.SignUpDetailWrapper;
import dev.kush.backend.customer.service.CustomerService;
import dev.kush.backend.customer.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }


    // login to account
    @PostMapping("/signin")
    public ResponseEntity<SendDetailWrapperWithJwt> login(@Valid @RequestBody LoginCustomerWrapper loginCustomer){
        return customerService.login(loginCustomer);
    }


    // create customer and account

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody SignUpDetailWrapper signUpDetailWrapper){
        return customerService.create(signUpDetailWrapper);
    }

    // delete customer and account

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody LoginCustomerWrapper loginCustomerWrapper){
        return customerService.deleteCustomer(loginCustomerWrapper);
    }




}
