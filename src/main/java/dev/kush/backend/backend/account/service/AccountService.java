package dev.kush.backend.backend.account.service;

import dev.kush.backend.backend.account.models.DepositMoneyWrapper;
import dev.kush.backend.backend.frontendDetail.model.SendDetailWrapper;
import dev.kush.backend.backend.account.models.TransferMoneyWrapper;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<SendDetailWrapper> getByAccountId(Long accountId);

    ResponseEntity<String> transfer(TransferMoneyWrapper transferMoneyWrapper);

    ResponseEntity<String> deposit(DepositMoneyWrapper depositMoneyWrapper);
}
