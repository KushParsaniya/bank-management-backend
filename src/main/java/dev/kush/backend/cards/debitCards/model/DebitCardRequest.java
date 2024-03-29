package dev.kush.backend.cards.debitCards.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.account.models.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebitCardRequest {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {DETACH,PERSIST,REFRESH,MERGE})
    @JsonManagedReference
    private Account account;

    public DebitCardRequest(Account account) {
        this.account = account;
    }
}
