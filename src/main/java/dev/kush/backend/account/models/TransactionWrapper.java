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
public class TransactionWrapper {
    private String date;
    private String time;
    private TransactionType description;
    @Min(value = 0,message = "amount should be greater than zero")
    private Long amount;
}
