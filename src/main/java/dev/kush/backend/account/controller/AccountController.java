package dev.kush.backend.account.controller;

import dev.kush.backend.account.models.DepositMoneyWrapper;
import dev.kush.backend.account.models.TransferMoneyWrapper;
import dev.kush.backend.account.service.AccountService;
import dev.kush.backend.customer.dto.SendDetailDto;
import jakarta.validation.Valid;
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
    public ResponseEntity<SendDetailDto> getByAccountId(@PathVariable Long accountId) {
        return accountService.getByAccountId(accountId);
    }


    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody TransferMoneyWrapper transferMoneyWrapper) {
        return accountService.transfer(transferMoneyWrapper);
    }

    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody DepositMoneyWrapper depositMoneyWrapper) {
        return accountService.deposit(depositMoneyWrapper);
    }
}
