package dev.kush.backend.backend.services.account;

import dev.kush.backend.backend.models.Account;
import dev.kush.backend.backend.models.Customer;
import dev.kush.backend.backend.models.features.Transaction;
import dev.kush.backend.backend.models.wrapper.SendDetailWrapper;
import dev.kush.backend.backend.models.wrapper.TransactionWrapper;
import dev.kush.backend.backend.repository.AccountRepository;
import dev.kush.backend.backend.repository.CustomerRepository;
import dev.kush.backend.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }



    @Override
    public ResponseEntity<SendDetailWrapper> getByAccountId(Long accountId) {
        try {
            // find account by accountId
            Account account = accountRepository.findById(accountId).orElse(null);
            if (account == null) {
                return new ResponseEntity<>(new SendDetailWrapper(), HttpStatus.NOT_FOUND);
            }

            Customer customer = account.getCustomer();

            if (customer == null) {
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
}
