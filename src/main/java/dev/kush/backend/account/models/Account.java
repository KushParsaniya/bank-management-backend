package dev.kush.backend.account.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.cards.creditCards.model.CreditCard;
import dev.kush.backend.cards.creditCards.model.CreditCardRequest;
import dev.kush.backend.cards.debitCards.model.DebitCard;
import dev.kush.backend.cards.debitCards.model.DebitCardRequest;
import dev.kush.backend.customer.model.Customer;
import dev.kush.backend.loans.model.Loan;
import dev.kush.backend.loans.model.LoanRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    // account number
    private Long id;
    @Min(value = 0 , message = "Balance should be greater than zero.")
    private Long balance;
    @Enumerated(EnumType.STRING)
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

    @OneToMany(mappedBy = "account",cascade = ALL)
    @JsonBackReference
    private List<DebitCardRequest> debitCardRequests;

    @OneToMany(mappedBy = "account",cascade = ALL)
    @JsonBackReference
    private List<LoanRequest> loanRequests;

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

    public Account(Long balance, AccountType accountType, Customer customer, List<CreditCard> creditCards, List<DebitCard> debitCards, List<Loan> loans, List<Transaction> transactions, List<CreditCardRequest> creditCardRequests, List<DebitCardRequest> debitCardRequests, List<LoanRequest> loanRequests) {
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
        this.creditCards = creditCards;
        this.debitCards = debitCards;
        this.loans = loans;
        this.transactions = transactions;
        this.creditCardRequests = creditCardRequests;
        this.debitCardRequests = debitCardRequests;
        this.loanRequests = loanRequests;
    }
}
