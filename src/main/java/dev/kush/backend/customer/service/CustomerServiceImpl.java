package dev.kush.backend.customer.service;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.account.models.Transaction;
import dev.kush.backend.account.models.TransactionWrapper;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.account.repository.TransactionRepository;
import dev.kush.backend.customer.model.*;
import dev.kush.backend.customer.repository.CustomerRepository;
import dev.kush.backend.exception.ConflictException;
import dev.kush.backend.exception.UnauthorizedUserException;
import dev.kush.backend.exception.UserNotFoundException;
import dev.kush.backend.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SecuredCustomerService securedCustomerService;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, SecuredCustomerService securedCustomerService, AccountRepository accountRepository, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.customerRepository = customerRepository;
        this.securedCustomerService = securedCustomerService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<SendDetailWrapperWithJwt> login(LoginCustomerWrapper loginCustomer) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginCustomer.getEmail(),
                            loginCustomer.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new UnauthorizedUserException("Wrong credentials");
        }

        UserDetails userDetails = securedCustomerService.loadUserByUsername(loginCustomer.getEmail());

        String token = jwtUtils.generateToken(userDetails);


        Customer customer = customerRepository.findCustomerByEmail(loginCustomer.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Customer with " + loginCustomer.getEmail() + " does not exist")

        );


        // get account associated with customer
        Account account = accountRepository.findReferenceByCustomerId(customer.getId()).orElseThrow(
                () -> new UserNotFoundException("No account found for customerId " + customer.getId())
        );

        // all transaction associated with the account
        List<Transaction> transactions = transactionRepository.findAllReferenceByAccountId(account.getId()).orElse(List.of());

        // now convert this transaction to transactionWrapper
        List<TransactionWrapper> transactionWrappers = transactions.stream().map(transaction ->
                new TransactionWrapper(transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getAmount())).toList();

//        for (Transaction transaction : transactions) {
//            transactionWrappers.add(new TransactionWrapper(
//                    transaction.getDate(),
//                    transaction.getTime(),
//                    transaction.getDescription(),
//                    transaction.getAmount()
//            ));
//        }

        SendDetailWrapper sendDetailWrapper = new SendDetailWrapper(
                customer.getName(),
                customer.getEmail(),
                account.getId(),
                account.getBalance(),
                account.getAccountType(),
                customer.getRole(),
                transactionWrappers
        );

        return new ResponseEntity<>(new SendDetailWrapperWithJwt(sendDetailWrapper,token), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> create(SignUpDetailWrapper signUpDetailWrapper) {

        // check if email already exist in our database
        Customer customerOptional = customerRepository.findCustomerByEmail(signUpDetailWrapper.getEmail()).orElse(null);

        if (!Objects.isNull(customerOptional)) {
            throw new ConflictException("user with email " + customerOptional.getEmail() + " is already exist.");
        }

        Customer customer = new Customer(
                signUpDetailWrapper.getUsername(),
                signUpDetailWrapper.getEmail(),
                passwordEncoder.encode(signUpDetailWrapper.getPassword()),
                "ROLE_USER",
                false,
                false
        );

        Account account = new Account(
                0L, signUpDetailWrapper.getAccountType(), customer
        );
        customer.setAccount(account);

        customerRepository.save(customer);
        return new ResponseEntity<>("successfully created", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteCustomer(LoginCustomerWrapper loginCustomerWrapper) {

        Customer customer = customerRepository.findCustomerByEmail(loginCustomerWrapper.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Customer with email " + loginCustomerWrapper.getEmail() + " not found.")
        );


        if (!passwordEncoder.matches(loginCustomerWrapper.getPassword(), customer.getPassword())) {
            throw new UnauthorizedUserException("Invalid password");
        }

        customerRepository.delete(customer);
        return new ResponseEntity<>("successfully deleted", HttpStatus.OK);

    }
}
