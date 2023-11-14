package dev.kush.backend.backend.account.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.backend.cards.creditCards.model.CreditCardRequest;
import dev.kush.backend.backend.customer.model.Customer;
import dev.kush.backend.backend.cards.creditCards.model.CreditCard;
import dev.kush.backend.backend.cards.debitCards.model.DebitCard;
import dev.kush.backend.backend.loans.model.Loan;
import dev.kush.backend.backend.transactions.model.Transaction;
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
    private Long balance;
    private AccountType accountType;

    @OneToOne(cascade = ALL)
    @JsonManagedReference
    private Customer customer;

    @OneToMany(mappedBy = "account",cascade = ALL)
    @JsonBackReference
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "account",cascade = ALL)
    @JsonBackReference
    private List<DebitCard> debitCards;

    @OneToMany(mappedBy = "account",cascade = ALL)
    @JsonBackReference
    private List<Loan> loans;

    @OneToMany(mappedBy = "account",cascade = ALL)
    @JsonBackReference
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "account",cascade = ALL)
    @JsonBackReference
    private List<CreditCardRequest> creditCardRequests;

    public Account(Long balance, AccountType accountType, Customer customer) {
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Account(Long balance, AccountType accountType, Customer customer, List<CreditCard> creditCards) {
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
        this.creditCards = creditCards;
    }

    public Account(Long balance, AccountType accountType, Customer customer, List<CreditCard> creditCards, List<DebitCard> debitCards) {
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
        this.creditCards = creditCards;
        this.debitCards = debitCards;
    }

    public Account(Long balance, AccountType accountType, Customer customer, List<CreditCard> creditCards, List<DebitCard> debitCards, List<Loan> loans) {
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
        this.creditCards = creditCards;
        this.debitCards = debitCards;
        this.loans = loans;
    }

    public Account(Long balance, AccountType accountType, Customer customer, List<CreditCard> creditCards, List<DebitCard> debitCards, List<Loan> loans, List<Transaction> transactions, List<CreditCardRequest> creditCardRequests) {
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
        this.creditCards = creditCards;
        this.debitCards = debitCards;
        this.loans = loans;
        this.transactions = transactions;
        this.creditCardRequests = creditCardRequests;
    }
}
