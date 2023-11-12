package dev.kush.backend.backend.account.service;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.customer.model.Customer;
import dev.kush.backend.backend.transactions.model.TransactionType;
import dev.kush.backend.backend.transactions.model.Transaction;
import dev.kush.backend.backend.frontendDetail.model.SendDetailWrapper;
import dev.kush.backend.backend.transactions.model.TransactionWrapper;
import dev.kush.backend.backend.transactions.model.TransferMoneyWrapper;
import dev.kush.backend.backend.account.repository.AccountRepository;
import dev.kush.backend.backend.customer.repository.CustomerRepository;
import dev.kush.backend.backend.transactions.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

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

    @Override
    @Transactional
    public ResponseEntity<String> transfer(TransferMoneyWrapper transferMoneyWrapper) {
        try {
            // find sender account by senderAccountID
            Account senderAccount = accountRepository.findById(transferMoneyWrapper.getSenderId()).orElse(null);
            if (senderAccount == null) {
                return new ResponseEntity<>("sender account not found", HttpStatus.NOT_FOUND);
            }
            if (senderAccount.getBalance() < transferMoneyWrapper.getAmount()){
                return new ResponseEntity<>("insufficient balance.", HttpStatus.BAD_REQUEST);
            }


            // find receiver account by receiverAccountID
            Account receiverAccount = accountRepository.findById(transferMoneyWrapper.getReceiverId()).orElse(null);

            if (receiverAccount == null) {
                return new ResponseEntity<>("receiver account not found", HttpStatus.NOT_FOUND);
            }

            // money deduct from sender account
            senderAccount.setBalance(senderAccount.getBalance() - transferMoneyWrapper.getAmount());

            // add money to receiver account
            receiverAccount.setBalance(receiverAccount.getBalance() + transferMoneyWrapper.getAmount());

            // now we have to add this to transfer table for both sender and receiver

            Transaction senderTransaction = new Transaction(LocalDate.now().toString(),
                    LocalTime.now().toString(),
                    TransactionType.TRANSFER,
                    transferMoneyWrapper.getAmount(),
                    senderAccount);

            Transaction receiverTransaction = new Transaction(LocalDate.now().toString(),
                    LocalTime.now().toString(),
                    TransactionType.DEPOSIT,
                    transferMoneyWrapper.getAmount(),
                    receiverAccount);

            transactionRepository.saveAll(List.of(senderTransaction,receiverTransaction));
            return new ResponseEntity<>("successfully transferred",HttpStatus.OK);

        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
