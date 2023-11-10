package dev.kush.backend.backend.controllers;

import dev.kush.backend.backend.models.Account;
import dev.kush.backend.backend.repository.AccountRepository;
import dev.kush.backend.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MyController {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public MyController(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}")
    public Account index(@PathVariable Long id) {
        return accountRepository.findById(id).orElse(null);
    }
}
