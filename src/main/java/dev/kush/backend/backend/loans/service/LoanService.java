package dev.kush.backend.backend.loans.service;

import dev.kush.backend.backend.loans.model.LoanWrapper;
import dev.kush.backend.backend.loans.model.ReceivedLoanWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoanService {
    ResponseEntity<List<LoanWrapper>> getAllLoan(Long id);

    ResponseEntity<String> createLoan(ReceivedLoanWrapper receivedLoanWrapper);
}
