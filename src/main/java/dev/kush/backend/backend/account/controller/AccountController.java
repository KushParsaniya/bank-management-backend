package dev.kush.backend.backend.account.controller;

import dev.kush.backend.backend.account.models.DepositMoneyWrapper;
import dev.kush.backend.backend.frontendDetail.model.SendDetailWrapper;
import dev.kush.backend.backend.account.models.TransferMoneyWrapper;
import dev.kush.backend.backend.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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



    @GetMapping("/getByAccountId/{accountId}")
    public ResponseEntity<SendDetailWrapper> getByAccountId(@PathVariable Long accountId) {
        return accountService.getByAccountId(accountId);
    }


    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferMoneyWrapper transferMoneyWrapper) {
        return accountService.transfer(transferMoneyWrapper);
    }

    @PutMapping("/deposit")
    public ResponseEntity<String> deposite(@RequestBody DepositMoneyWrapper depositMoneyWrapper) {
        return accountService.deposite(depositMoneyWrapper);
    }
}
