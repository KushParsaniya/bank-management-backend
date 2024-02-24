package dev.kush.backend.customer.service.impl;

import dev.kush.backend.customer.repository.CustomerRepository;
import dev.kush.backend.customer.service.SecuredCustomerService;
import dev.kush.backend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecuredCustomerServiceImpl implements SecuredCustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public SecuredCustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findCustomerByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User with email " + username + " not found")
        );
    }
}
