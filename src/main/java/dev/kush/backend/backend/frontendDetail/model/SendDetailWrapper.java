package dev.kush.backend.backend.frontendDetail.model;

import dev.kush.backend.backend.account.models.AccountType;
import dev.kush.backend.backend.transactions.model.TransactionWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendDetailWrapper {
    private String username;
    private String email;
    private Long accountId;
    private Long Balance;
    private AccountType type;
    private List<TransactionWrapper> transactions;

}
