package dev.kush.backend.backend.services.cards.creditCard;

import org.springframework.http.ResponseEntity;

public interface CreditCardService {
    ResponseEntity<String> createCreditCard(Long accountId);
}
