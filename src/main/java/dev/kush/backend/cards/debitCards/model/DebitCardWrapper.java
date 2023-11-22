package dev.kush.backend.cards.debitCards.model;

import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebitCardWrapper {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
