package dev.kush.backend.backend.services.cards.debitCard;

import org.springframework.http.ResponseEntity;

public interface DebitCardService {
    ResponseEntity<String> createDebitCard(Long accountId);
}
