package dev.kush.backend.customer.service;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.account.models.Transaction;
import dev.kush.backend.account.models.TransactionWrapper;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.account.repository.TransactionRepository;
import dev.kush.backend.customer.model.Customer;
import dev.kush.backend.customer.model.SendDetailWrapper;
import dev.kush.backend.customer.repository.CustomerRepository;
import dev.kush.backend.exception.ConflictException;
import dev.kush.backend.exception.UnauthorizedUserException;
import dev.kush.backend.exception.UserNotFoundException;
import dev.kush.backend.customer.model.LoginCustomerWrapper;
import dev.kush.backend.customer.model.SignUpDetailWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static dev.kush.backend.customer.model.Role.USER;

/**
 * This class implements the CustomerService interface and provides the implementation
 * for the login, create, and deleteCustomer methods. It retrieves data from the
 * customerRepository, accountRepository, and transactionRepository to perform the required operations.
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<SendDetailWrapper> login(LoginCustomerWrapper loginCustomer) {
         // customer which is saved in database
            Customer customer = customerRepository.findCustomerByEmail(loginCustomer.getEmail()).orElseThrow(
                    () -> new UserNotFoundException("Customer with " + loginCustomer.getEmail() + " does not exist")

            );

            // match received password with existing password
            if(!passwordEncoder.matches(loginCustomer.getPassword(), customer.getPassword())) {
                throw new UnauthorizedUserException("Invalid password");
            }

            // get account associated with customer
            Account account = accountRepository.findReferenceByCustomerId(customer.getId()).orElseThrow(
                    () -> new UserNotFoundException("No account found for customerId " + customer.getId())
            );

            // all transaction associated with the account
            List<Transaction> transactions = transactionRepository.findAllReferenceByAccountId(account.getId()).orElse(List.of());

            // now convert this transaction to transactionWrapper
            List<TransactionWrapper> transactionWrappers = transactions.stream().map(transaction -> new TransactionWrapper(
                            transaction.getDate(),
                            transaction.getTime(),
                            transaction.getDescription(),
                            transaction.getAmount()
                    )
            ).collect(Collectors.toList());

            // creating a wrapper to send to frontend which is SendDetailWrapper

            SendDetailWrapper sendDetailWrapper = new SendDetailWrapper(
                    customer.getUserName(),
                    customer.getEmail(),
                    account.getId(),
                    account.getBalance(),
                    account.getAccountType(),
                    customer.getRole(),
                    transactionWrappers
            );

            return new ResponseEntity<>(sendDetailWrapper, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> create(SignUpDetailWrapper signUpDetailWrapper) {

            // check if email already exist in our database
            Customer customerOptional = customerRepository.findCustomerByEmail(signUpDetailWrapper.getEmail()).orElse(null);

            if (!Objects.isNull(customerOptional)){
                throw new ConflictException("user with email " + customerOptional.getEmail() + " is already exist.");
            }

            Customer customer = new Customer(
                    signUpDetailWrapper.getUsername(),
                    signUpDetailWrapper.getEmail(),
                    signUpDetailWrapper.getPassword(),
                    USER
            );

            Account account = new Account(
                0L, signUpDetailWrapper.getAccountType(),customer
            );
            customer.setAccount(account);

            customerRepository.save(customer);
            return new ResponseEntity<>("successfully created",HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteCustomer(LoginCustomerWrapper loginCustomerWrapper) {

            Customer customer = customerRepository.findCustomerByEmail(loginCustomerWrapper.getEmail()).orElseThrow(
                    () -> new UserNotFoundException("Customer with email " + loginCustomerWrapper.getEmail() + " not found.")
            );


        if(!passwordEncoder.matches(loginCustomerWrapper.getPassword(), customer.getPassword())) {
            throw new UnauthorizedUserException("Invalid password");
            }

            customerRepository.delete(customer);
            return new ResponseEntity<>("successfully deleted",HttpStatus.OK);

    }

}
