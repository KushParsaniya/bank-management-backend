package dev.kush.backend.backend.customer.service;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.customer.model.Customer;
import dev.kush.backend.backend.transactions.model.Transaction;
import dev.kush.backend.backend.frontendDetail.model.LoginCustomerWrapper;
import dev.kush.backend.backend.frontendDetail.model.SendDetailWrapper;
import dev.kush.backend.backend.frontendDetail.model.SignUpDetailWrapper;
import dev.kush.backend.backend.transactions.model.TransactionWrapper;
import dev.kush.backend.backend.account.repository.AccountRepository;
import dev.kush.backend.backend.customer.repository.CustomerRepository;
import dev.kush.backend.backend.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ResponseEntity<SendDetailWrapper> login(LoginCustomerWrapper loginCustomer) {
        try {

            // customer which is saved in database
            Customer customer = customerRepository.findCustomerByEmail(loginCustomer.getEmail()).orElse(null);
            if (customer == null) {
                return new ResponseEntity<>(new SendDetailWrapper(), HttpStatus.NOT_FOUND);
            }

            // match received password with existing password
            if(!customer.getPassword().equals(loginCustomer.getPassword())) {
                return new ResponseEntity<>(new SendDetailWrapper(),HttpStatus.UNAUTHORIZED);
            }
            // get account associated with customer
            Account account = accountRepository.findReferenceByCustomerId(customer.getId()).orElse(null);
            if (account == null) {
                return new ResponseEntity<>(new SendDetailWrapper(), HttpStatus.NOT_FOUND);
            }

            // all transaction associated with the account
            List<Transaction> transactions = transactionRepository.findAllReferenceByAccountId(account.getId()).orElse(null);

            // now convert this transaction to transactionWrapper
            List<TransactionWrapper> transactionWrappers = new ArrayList<>();

            for(Transaction transaction: transactions){
                transactionWrappers.add(new TransactionWrapper(
                       transaction.getDate(),
                       transaction.getTime(),
                       transaction.getDescription(),
                       transaction.getAmount()
                ));
            }

            // creating a wrapper to send to frontend which is SendDetailWrapper

            SendDetailWrapper sendDetailWrapper = new SendDetailWrapper(
                    customer.getUserName(),
                    customer.getEmail(),
                    account.getId(),
                    account.getBalance(),
                    account.getAccountType(),
                    transactionWrappers
            );

            return new ResponseEntity<>(sendDetailWrapper, HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new SendDetailWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> create(SignUpDetailWrapper signUpDetailWrapper) {
        try{

            // check if email already exist in our database
            Customer customerOptional = customerRepository.findCustomerByEmail(signUpDetailWrapper.getEmail()).orElse(null);
            if (!Objects.isNull(customerOptional)){
                return new ResponseEntity<>("customer with email already exists.",HttpStatus.CONFLICT);
            }

            Customer customer = new Customer(
                    signUpDetailWrapper.getUsername(),
                    signUpDetailWrapper.getEmail(),
                    signUpDetailWrapper.getPassword()
            );

            Account account = new Account(
                0L, signUpDetailWrapper.getAccountType(),customer
            );
            customer.setAccount(account);

            customerRepository.save(customer);
            return new ResponseEntity<>("successfully created",HttpStatus.OK);

        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCustomer(LoginCustomerWrapper loginCustomerWrapper) {
        try {
            Customer customer = customerRepository.findCustomerByEmail(loginCustomerWrapper.getEmail()).orElse(null);

            if (customer == null) {
                return new ResponseEntity<>("customer with email doesn't exist.", HttpStatus.NOT_FOUND);
            }

            if (!customer.getPassword().equals(loginCustomerWrapper.getPassword())){
                return new ResponseEntity<>("please, Enter valid credentials ",HttpStatus.UNAUTHORIZED);
            }
            customerRepository.delete(customer);
            return new ResponseEntity<>("successfully deleted",HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
