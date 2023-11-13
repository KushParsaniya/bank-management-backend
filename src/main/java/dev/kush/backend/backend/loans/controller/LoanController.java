package dev.kush.backend.backend.loans.controller;

import dev.kush.backend.backend.loans.model.LoanWrapper;
import dev.kush.backend.backend.loans.model.ReceivedLoanWrapper;
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

    @PostMapping("/createLoan")
    public ResponseEntity<String> createLoan(@RequestBody ReceivedLoanWrapper receivedLoanWrapper){
        return loanService.createLoan(receivedLoanWrapper);
    }
}
