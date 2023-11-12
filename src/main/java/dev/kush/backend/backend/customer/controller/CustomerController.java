package dev.kush.backend.backend.customer.controller;

import dev.kush.backend.backend.frontendDetail.model.LoginCustomerWrapper;
import dev.kush.backend.backend.frontendDetail.model.SendDetailWrapper;
import dev.kush.backend.backend.frontendDetail.model.SignUpDetailWrapper;
import dev.kush.backend.backend.customer.service.CustomerService;
import dev.kush.backend.backend.customer.service.CustomerServiceImpl;
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
    @CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<SendDetailWrapper> login(@RequestBody LoginCustomerWrapper loginCustomer){
        return customerService.login(loginCustomer);
    }


    // create customer and account
    @CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody SignUpDetailWrapper signUpDetailWrapper){
        return customerService.create(signUpDetailWrapper);
    }

    @CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody LoginCustomerWrapper loginCustomerWrapper){
        return customerService.deleteCustomer(loginCustomerWrapper);
    }




}
