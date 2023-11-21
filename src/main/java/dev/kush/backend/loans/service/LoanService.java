package dev.kush.backend.loans.service;

import dev.kush.backend.loans.model.LoanWrapper;
import dev.kush.backend.loans.model.ReceivedLoanWrapper;
import dev.kush.backend.loans.model.SendRequestLoan;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoanService {
    ResponseEntity<List<LoanWrapper>> getAllLoan(Long id);

    ResponseEntity<String> createLoan(Long requestId);

    ResponseEntity<String> applyLoan(ReceivedLoanWrapper receivedLoanWrapper);

    ResponseEntity<String> deleteLoanRequest(Long requestId);

    ResponseEntity<List<SendRequestLoan>> getAllLoanRequests();
}
