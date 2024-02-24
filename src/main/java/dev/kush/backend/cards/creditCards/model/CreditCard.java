package dev.kush.backend.cards.creditCards.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.account.models.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CreditCard {
    @Id  @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String cardNumber;
    private String cvv;
    @Min(value = 0,message = "credit limit should be greater than zero")
    private Long creditLimit;
    private Long remainingLimit;
    private String expirationDate;

    @ManyToOne(cascade = {DETACH,MERGE,REFRESH,PERSIST})
    @JsonManagedReference
    private Account account;

    public CreditCard(String cardNumber, String cvv, Long creditLimit, Long remainingLimit, String expirationDate, Account account) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.creditLimit = creditLimit;
        this.remainingLimit = remainingLimit;
        this.expirationDate = expirationDate;
        this.account = account;
    }
}
