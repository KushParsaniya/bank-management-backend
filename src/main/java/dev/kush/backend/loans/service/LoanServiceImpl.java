package dev.kush.backend.loans.service;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.customer.model.Customer;
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
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> createLoan(Long requstId) {
        try {
            LoanRequest loanRequest = loanRequestRepository.findById(requstId).orElse(null);

            if (loanRequest == null) {
                return new ResponseEntity<>("request not fount",HttpStatus.NOT_FOUND);
            }

            Account account = loanRequest.getAccount();

            if (account == null) {
                return new ResponseEntity<>("account not found", HttpStatus.NOT_FOUND);
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

        } catch (Exception e){
          e.printStackTrace();
        }
        return new ResponseEntity<>("server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> applyLoan(ReceivedLoanWrapper receivedLoanWrapper) {
        try {
            Account account = accountRepository.findById(receivedLoanWrapper.getAccountId()).orElse(null);

            if(account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            }

            LoanRequest loanRequest = new LoanRequest(
                    receivedLoanWrapper.getLoanAmount(),
                    getInterestService.getInterest(receivedLoanWrapper.getLoanType()),
                receivedLoanWrapper.getLoanType(),
                account
            );

            account.setLoanRequests(List.of(loanRequest));

            loanRequestRepository.save(loanRequest);
            return new ResponseEntity<>("successfully applied for loan", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteLoanRequest(Long requestId) {
        try {
            LoanRequest loanRequest = loanRequestRepository.findById(requestId).orElse(null);

            if (loanRequest == null) {
                return new ResponseEntity<>("request not found",HttpStatus.NOT_FOUND);
            }

            loanRequestRepository.delete(loanRequest);
            return new ResponseEntity<>("successfully deleted",HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SendRequestLoan>> getAllLoanRequests() {
        try {
            List<LoanRequest> loanRequests = loanRequestRepository.findAll();

            if (loanRequests.isEmpty()) {
                return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(List.of(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
