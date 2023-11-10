package dev.kush.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.backend.models.enums.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    // account number
    private Long id;
    private Long Balance;
    private AccountType accountType;

    @OneToOne(cascade = {DETACH,REFRESH,MERGE})
    @JsonManagedReference
    private Customer customer;

    public Account(Long balance, AccountType accountType, Customer customer) {
        Balance = balance;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Account(Long balance, AccountType accountType) {
        Balance = balance;
        this.accountType = accountType;
    }
}
