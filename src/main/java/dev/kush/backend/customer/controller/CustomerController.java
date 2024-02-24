package dev.kush.backend.customer.controller;

import dev.kush.backend.customer.dto.LoginCustomerDto;
import dev.kush.backend.customer.dto.SendDetailDtoWithJwt;
import dev.kush.backend.customer.dto.SignUpDetailDto;
import dev.kush.backend.customer.service.CustomerService;
import dev.kush.backend.customer.service.impl.CustomerServiceImpl;
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
    public ResponseEntity<SendDetailDtoWithJwt> login(@Valid @RequestBody LoginCustomerDto loginCustomer){
        return customerService.login(loginCustomer);
    }


    // create customer and account

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody SignUpDetailDto signUpDetailDto){
        return customerService.create(signUpDetailDto);
    }

    // delete customer and account

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> delete(@Valid @PathVariable Long customerId){
        return customerService.deleteCustomer(customerId);
    }

    @GetMapping("/confirm")
    public String confirmToken(@RequestParam String token){
        return customerService.confirmToken(token);
    }




}
