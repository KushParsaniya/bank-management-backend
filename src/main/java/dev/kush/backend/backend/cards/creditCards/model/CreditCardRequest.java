package dev.kush.backend.backend.cards.creditCards.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.backend.account.models.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardRequest {
    @Id @GeneratedValue
    private Long id;
    private Long appliedAmount;

    @ManyToOne(cascade = {DETACH,REFRESH,PERSIST,MERGE})
    @JsonManagedReference
    private Account account;

    public CreditCardRequest(Long appliedAmount, Account account) {
        this.appliedAmount = appliedAmount;
        this.account = account;
    }
}
