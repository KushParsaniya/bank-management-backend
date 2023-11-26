package dev.kush.backend.account.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String time;
    @Enumerated(value = EnumType.STRING)
    private TransactionType description;
    @Min(value = 0,message = "amount should be greater than zero")
    private Long amount;

    @ManyToOne(cascade = ALL)
    @JsonManagedReference
    private Account account;

    public Transaction(String date, String time, TransactionType description, Long amount, Account account) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.amount = amount;
        this.account = account;
    }
}
