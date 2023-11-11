package dev.kush.backend.backend.models.wrapper;

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
    private String expiryDate;
    private Long creditLimit;
    private Long usedCreditLimit;
}
