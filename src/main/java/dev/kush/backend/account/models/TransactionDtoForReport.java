package dev.kush.backend.account.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDtoForReport {
    private String date;
    private String time;
    private String description;
    private Long amount;

    public TransactionDtoForReport(Transaction transaction) {
        this.date = transaction.getDate();
        this.time = transaction.getTime().substring(0,12);
        this.description = transaction.getDescription().toString();
        this.amount = transaction.getAmount();
    }
}
