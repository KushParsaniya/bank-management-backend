package dev.kush.backend.backend.services.customer;

import dev.kush.backend.backend.models.Account;
import dev.kush.backend.backend.models.Customer;
import dev.kush.backend.backend.models.enums.AccountType;
import dev.kush.backend.backend.models.features.Transaction;
import dev.kush.backend.backend.models.wrapper.LoginCustomerWrapper;
import dev.kush.backend.backend.models.wrapper.SendDetailWrapper;
import dev.kush.backend.backend.models.wrapper.SignUpDetailWrapper;
import dev.kush.backend.backend.models.wrapper.TransactionWrapper;
import dev.kush.backend.backend.repository.AccountRepository;
import dev.kush.backend.backend.repository.CreditCardRepository;
import dev.kush.backend.backend.repository.CustomerRepository;
import dev.kush.backend.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.kush.backend.backend.models.enums.AccountType.CURRENT;

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
            Customer customer = new Customer(
                    signUpDetailWrapper.getUserName(),
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
}
