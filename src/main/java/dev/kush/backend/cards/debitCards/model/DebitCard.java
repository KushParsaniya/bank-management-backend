package dev.kush.backend.cards.debitCards.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.account.models.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DebitCard {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String cardNumber;
    private String cvv;
    private String expirationDate;

    @ManyToOne(cascade = ALL)
    @JsonManagedReference
    private Account account;

    public DebitCard(String cardNumber, String cvv, String expirationDate, Account account) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.account = account;
    }
}
