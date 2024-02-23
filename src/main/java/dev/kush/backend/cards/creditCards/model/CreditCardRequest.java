package dev.kush.backend.cards.creditCards.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.account.models.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
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
    @Min(value = 0,message = "applied amount should be greater than zero")
    private Long appliedAmount;

    @ManyToOne(cascade = {DETACH,REFRESH,PERSIST,MERGE})
    @JsonManagedReference
    private Account account;

    public CreditCardRequest(Long appliedAmount, Account account) {
        this.appliedAmount = appliedAmount;
        this.account = account;
    }
}
