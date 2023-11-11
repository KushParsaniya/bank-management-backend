package dev.kush.backend.backend.models.wrapper;

import dev.kush.backend.backend.models.enums.AccountType;
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
