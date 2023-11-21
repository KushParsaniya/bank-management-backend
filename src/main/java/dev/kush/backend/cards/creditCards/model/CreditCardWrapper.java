package dev.kush.backend.cards.creditCards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardWrapper {
    private String cardNumber;
    private String expirationDate;
    private Long creditLimit;
    private Long usedCreditLimit;
    private String cvv;
}
