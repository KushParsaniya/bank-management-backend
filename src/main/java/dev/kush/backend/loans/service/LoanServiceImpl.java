package dev.kush.backend.loans.service;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.customer.model.Customer;
import dev.kush.backend.exception.UserNotFoundException;
import dev.kush.backend.loans.model.*;
import dev.kush.backend.loans.repository.LoanRepository;
import dev.kush.backend.loans.repository.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;
    private final GetInterestService getInterestService;
    private final AccountRepository accountRepository;
    private final LoanRequestRepository loanRequestRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, GetInterestService getInterestService, AccountRepository accountRepository, LoanRequestRepository loanRequestRepository) {
        this.loanRepository = loanRepository;
        this.getInterestService = getInterestService;
        this.accountRepository = accountRepository;
        this.loanRequestRepository = loanRequestRepository;
    }

    // get all active loan
    @Override
    public ResponseEntity<List<LoanWrapper>> getAllLoan(Long id) {
            List<Loan> loans = loanRepository.findAllReferenceByAccountId(id).orElse(List.of());

            // check if no loan found
            if (loans.isEmpty()){
                return new ResponseEntity<>(List.of(),HttpStatus.OK);
            }

            List<LoanWrapper> loanWrappers = new ArrayList<>();

            for(Loan loan:loans){
                loanWrappers.add(
                        new LoanWrapper(
                                loan.getLoanType(),
                                loan.getLoanAmount(),
                                loan.getInterest()
                        )
                );
            }
            return new ResponseEntity<>(loanWrappers,HttpStatus.OK);


    }

    @Override
    public ResponseEntity<String> createLoan(Long requstId) {
            LoanRequest loanRequest = loanRequestRepository.findById(requstId).orElseThrow(
                    () -> new UserNotFoundException("Loan request not found")
            );


            Account account = loanRequest.getAccount();

            if (account == null) {
                throw new UserNotFoundException("Account not found");
            }

            Loan loan = new Loan(
                    loanRequest.getLoanType(),
                    loanRequest.getAppliedAmount(),
                    loanRequest.getLoanInterest(),
                    account
            );


            account.setLoans(List.of(loan));
            loanRepository.save(loan);
            return new ResponseEntity<>("successfully created loan",HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> applyLoan(ReceivedLoanWrapper receivedLoanWrapper) {

            Account account = accountRepository.findById(receivedLoanWrapper.getAccountId()).orElseThrow(
                    () -> new UserNotFoundException("Account with id " + receivedLoanWrapper.getAccountId() + " does not exist")
            );

            LoanRequest loanRequest = new LoanRequest(
                    receivedLoanWrapper.getLoanAmount(),
                    getInterestService.getInterest(receivedLoanWrapper.getLoanType()),
                receivedLoanWrapper.getLoanType(),
                account
            );

            account.setLoanRequests(List.of(loanRequest));

            loanRequestRepository.save(loanRequest);
            return new ResponseEntity<>("successfully applied for loan", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteLoanRequest(Long requestId) {

        LoanRequest loanRequest = loanRequestRepository.findById(requestId).orElseThrow(
                () -> new UserNotFoundException("Loan request not found")
        );

        loanRequestRepository.delete(loanRequest);
        return new ResponseEntity<>("successfully deleted", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<SendRequestLoan>> getAllLoanRequests() {
            List<LoanRequest> loanRequests = loanRequestRepository.findAll();

            if (loanRequests.isEmpty()) {
                return new ResponseEntity<>(List.of(), HttpStatus.OK);
            }

            List<SendRequestLoan> sendRequestLoans = new ArrayList<>();

            for(LoanRequest loanRequest : loanRequests) {
                Account account = loanRequest.getAccount();

                if (account == null) {
                    continue;
                }

                Customer customer = account.getCustomer();

                if (customer == null) {
                    continue;
                }

                sendRequestLoans.add(new SendRequestLoan(
                        customer.getUserName(),
                        customer.getEmail(),
                        account.getBalance(),
                        account.getId(),
                        loanRequest.getId(),
                        loanRequest.getAppliedAmount(),
                        getInterestService.getInterest(loanRequest.getLoanType()),
                        loanRequest.getLoanType()
                ));
            }

            return new ResponseEntity<>(sendRequestLoans,HttpStatus.OK);

    }

}
