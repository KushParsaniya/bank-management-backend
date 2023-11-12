package dev.kush.backend.backend.cards.creditCards.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.backend.account.models.Account;
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
public class CreditCard {
    @Id  @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String cardNumber;
    private String cvv;
    private Long creditLimit;
    private Long remainingLimit;
    private String expirationDate;

    @ManyToOne(cascade = ALL)
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
