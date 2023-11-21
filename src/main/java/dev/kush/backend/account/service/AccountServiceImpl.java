package dev.kush.backend.account.service;

import dev.kush.backend.account.models.*;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.account.repository.TransactionRepository;
import dev.kush.backend.customer.model.Customer;
import dev.kush.backend.customer.repository.CustomerRepository;
import dev.kush.backend.frontendDetail.model.SendDetailWrapper;
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
            if (accountId == null) {
                return new ResponseEntity<>(new SendDetailWrapper(), HttpStatus.BAD_REQUEST);
            }
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
                    customer.getRole(),
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
                return new ResponseEntity<>("insufficient balance.", HttpStatus.UNPROCESSABLE_ENTITY);
            }


            // find receiver account by receiverAccountID
            Account receiverAccount = accountRepository.findById(transferMoneyWrapper.getReceiverId()).orElse(null);

            if (receiverAccount == null) {
                return new ResponseEntity<>("receiver account not found", HttpStatus.NOT_FOUND);
            }

            if (senderAccount.getId() == receiverAccount.getId()) {
                return new ResponseEntity<>("sender and receiver cannot be the same account", HttpStatus.BAD_REQUEST);
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

    @Transactional
    @Override
    public ResponseEntity<String> deposit(DepositMoneyWrapper depositMoneyWrapper) {
        try {
            Account account = accountRepository.findById(depositMoneyWrapper.getAccountId()).orElse(null);

            if (account == null){
                return new ResponseEntity<>("account not found",HttpStatus.NOT_FOUND);
            }

            account.setBalance(account.getBalance() + depositMoneyWrapper.getAmount());

            // also we have to add it to transaction table
            Transaction transaction = new Transaction(
                    LocalDate.now().toString(),
                    LocalTime.now().toString(),
                    TransactionType.DEPOSIT,
                    depositMoneyWrapper.getAmount(),
                    account
            );
            transactionRepository.save(transaction);
            return new ResponseEntity<>("successfully deposit",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
