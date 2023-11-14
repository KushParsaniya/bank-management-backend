package dev.kush.backend.backend.cards.creditCards.service;

import dev.kush.backend.backend.cards.creditCards.model.CreditCardWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CreditCardService {
    ResponseEntity<String> createCreditCard(Long accountId);

    ResponseEntity<List<CreditCardWrapper>> getCreditCard(Long accountId);

    ResponseEntity<String> reqCreditCard(Long accountId);

    ResponseEntity<String> deleteRequestCreditCard(Long requestId);
}
