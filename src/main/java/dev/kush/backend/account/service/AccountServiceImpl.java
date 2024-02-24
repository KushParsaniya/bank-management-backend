package dev.kush.backend.account.service;

import dev.kush.backend.account.models.*;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.account.repository.TransactionRepository;
import dev.kush.backend.customer.model.Customer;
import dev.kush.backend.customer.dto.SendDetailDto;
import dev.kush.backend.customer.repository.CustomerRepository;
import dev.kush.backend.exception.BadRequestException;
import dev.kush.backend.exception.UnprocessableEntityException;
import dev.kush.backend.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<SendDetailDto> getByAccountId(Long accountId) {

            if (accountId == null) {
                 throw new BadRequestException("Please enter a valid accountId");
            }
            // find account by accountId
            Account account = accountRepository.findById(accountId).orElseThrow(() -> new UserNotFoundException("" +
                    "user with id " + accountId + " does not exist"));
            Customer customer = account.getCustomer();

            // all transaction associated with the account
            List<Transaction> transactions = transactionRepository.findAllReferenceByAccountId(account.getId()).orElse(List.of());

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

            SendDetailDto sendDetailDto = new SendDetailDto(
                    customer.getName(),
                    customer.getEmail(),
                    account.getId(),
                    account.getBalance(),
                    account.getAccountType(),
                    customer.getRole(),
                    transactionWrappers
            );

            return new ResponseEntity<>(sendDetailDto, HttpStatus.OK);

    }

    @Override
    @Transactional
    public ResponseEntity<String> transfer(TransferMoneyWrapper transferMoneyWrapper) {
            // find sender account by senderAccountID
            Account senderAccount = accountRepository.findById(transferMoneyWrapper.getSenderId()).orElseThrow(
                    () -> new UserNotFoundException("Sender account not found")
            );

            // check if balance is sufficient
            if (senderAccount.getBalance() < transferMoneyWrapper.getAmount()){
                throw new UnprocessableEntityException("insufficient balance");
            }

            // find receiver account by receiverAccountID
            Account receiverAccount = accountRepository.findById(transferMoneyWrapper.getReceiverId()).orElseThrow(
                    () -> new UserNotFoundException("receiver account not found"));

            if (Objects.equals(senderAccount.getId(), receiverAccount.getId())) {
                throw new BadRequestException("You cannot transfer money to yourself.");
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

    }

    @Transactional
    @Override
    public ResponseEntity<String> deposit(DepositMoneyWrapper depositMoneyWrapper) {

            Account account = accountRepository.findById(depositMoneyWrapper.getAccountId()).orElseThrow(
                    () -> new UserNotFoundException("User with id " + depositMoneyWrapper.getAccountId() + " does not exist"));


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

    }
}
