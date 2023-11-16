package dev.kush.backend.backend.loans.controller;

import dev.kush.backend.backend.loans.model.LoanWrapper;
import dev.kush.backend.backend.loans.model.ReceivedLoanWrapper;
import dev.kush.backend.backend.loans.model.SendRequestLoan;
import dev.kush.backend.backend.loans.service.LoanService;
import dev.kush.backend.backend.loans.service.LoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account/info/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/getLoan/{id}")
    public ResponseEntity<List<LoanWrapper>> getAllLoan(@PathVariable Long id){
        return loanService.getAllLoan(id);
    }

    @GetMapping("/createLoan/{requestId}")
    public ResponseEntity<String> createLoan(@PathVariable Long requestId){
        return loanService.createLoan(requestId);
    }

    @PostMapping("/applyLoan")
    public ResponseEntity<String> applyLoan(@RequestBody ReceivedLoanWrapper receivedLoanWrapper){
        return loanService.applyLoan(receivedLoanWrapper);
    }

    @DeleteMapping("/deleteLoanRequest/{requestId}")
    public ResponseEntity<String> deleteLoanRequest(@PathVariable Long requestId){
        return loanService.deleteLoanRequest(requestId);
    }

    @GetMapping("getAllLoanRequests")
    public ResponseEntity<List<SendRequestLoan>> getAllLoanRequests(){
        return loanService.getAllLoanRequests();
    }
}
