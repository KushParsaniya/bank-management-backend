package dev.kush.backend.backend.account.models;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositMoneyWrapper {
    private Long accountId;
    private Long amount;
}
