package dev.kush.backend.backend.controllers;

import dev.kush.backend.backend.models.wrapper.LoginCustomerWrapper;
import dev.kush.backend.backend.models.wrapper.SendDetailWrapper;
import dev.kush.backend.backend.services.customer.CustomerService;
import dev.kush.backend.backend.services.customer.CustomerServiceImpl;
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

    @CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<SendDetailWrapper> login(@RequestBody LoginCustomerWrapper loginCustomer){
        return customerService.login(loginCustomer);
    }
}
