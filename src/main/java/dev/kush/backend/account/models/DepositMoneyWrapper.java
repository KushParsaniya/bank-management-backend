package dev.kush.backend.account.models;


import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositMoneyWrapper {
    private Long accountId;
    @Min(value = 0,message = "amount should be greater than zero")
    private Long amount;

}
