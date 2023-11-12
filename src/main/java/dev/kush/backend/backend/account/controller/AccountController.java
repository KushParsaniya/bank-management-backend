package dev.kush.backend.backend.account.controller;

import dev.kush.backend.backend.frontendDetail.model.SendDetailWrapper;
import dev.kush.backend.backend.transactions.model.TransferMoneyWrapper;
import dev.kush.backend.backend.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account/info")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*")
    @GetMapping("/getByAccountId/{accountId}")
    public ResponseEntity<SendDetailWrapper> getByAccountId(@PathVariable Long accountId) {
        return accountService.getByAccountId(accountId);
    }

    @CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferMoneyWrapper transferMoneyWrapper) {
        return accountService.transfer(transferMoneyWrapper);
    }
}
