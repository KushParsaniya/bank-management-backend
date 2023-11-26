package dev.kush.backend.account.models;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyWrapper {
    private Long senderId;
    private Long receiverId;
    @Min(value = 0,message = "amount should be greater than zero")
    private Long amount;
}
