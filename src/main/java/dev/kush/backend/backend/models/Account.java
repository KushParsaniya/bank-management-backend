package dev.kush.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.backend.models.enums.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToOne(cascade = ALL)
    @JsonManagedReference
    private Customer customer;

    @OneToMany(mappedBy = "account",cascade = ALL)
    @JsonBackReference
    private List<CreditCard> creditCards;

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
