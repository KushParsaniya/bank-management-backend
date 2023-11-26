package dev.kush.backend.account.service;

import dev.kush.backend.account.models.DepositMoneyWrapper;
import dev.kush.backend.customer.model.SendDetailWrapper;
import dev.kush.backend.account.models.TransferMoneyWrapper;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<SendDetailWrapper> getByAccountId(Long accountId);

    ResponseEntity<String> transfer(TransferMoneyWrapper transferMoneyWrapper);

    ResponseEntity<String> deposit(DepositMoneyWrapper depositMoneyWrapper);
}
