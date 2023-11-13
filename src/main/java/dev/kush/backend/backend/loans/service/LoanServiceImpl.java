package dev.kush.backend.backend.loans.service;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.account.repository.AccountRepository;
import dev.kush.backend.backend.loans.model.Loan;
import dev.kush.backend.backend.loans.model.LoanWrapper;
import dev.kush.backend.backend.loans.model.ReceivedLoanWrapper;
import dev.kush.backend.backend.loans.repository.LoanRepository;
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

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, GetInterestServiceImpl getInterestService, AccountRepository accountRepository) {
        this.loanRepository = loanRepository;
        this.getInterestService = getInterestService;
        this.accountRepository = accountRepository;
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
    public ResponseEntity<String> createLoan(ReceivedLoanWrapper receivedLoanWrapper) {
        try {
            Account account = accountRepository.findById(receivedLoanWrapper.getAccountId()).orElse(null);

            if (account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            }

            Loan loan = new Loan(
                    receivedLoanWrapper.getLoanType(),
                    receivedLoanWrapper.getLoanAmount(),
                    getInterestService.getInterest(receivedLoanWrapper.getLoanType()),
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
}
