package dev.kush.backend.cards.creditCards.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
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
    @Min(value = 0,message = "credit limit should be greater than zero")
    private Long creditLimit;
    private Long usedCreditLimit;
    private String cvv;
}
