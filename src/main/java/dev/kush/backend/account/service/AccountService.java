package dev.kush.backend.account.service;

import dev.kush.backend.account.models.DepositMoneyWrapper;
import dev.kush.backend.account.models.TransferMoneyWrapper;
import dev.kush.backend.customer.dto.SendDetailDto;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<SendDetailDto> getByAccountId(Long accountId);

    ResponseEntity<String> transfer(TransferMoneyWrapper transferMoneyWrapper);

    ResponseEntity<String> deposit(DepositMoneyWrapper depositMoneyWrapper);
}
