package dev.kush.backend.customer.model;

import dev.kush.backend.account.models.AccountType;
import dev.kush.backend.customer.model.Role;
import dev.kush.backend.account.models.TransactionWrapper;
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
    private Role role;
    private List<TransactionWrapper> transactions;

}
