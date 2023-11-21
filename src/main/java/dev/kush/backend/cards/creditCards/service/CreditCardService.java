package dev.kush.backend.cards.creditCards.service;

import dev.kush.backend.cards.creditCards.model.CreditCardWrapper;
import dev.kush.backend.cards.creditCards.model.SendCreditCardReqWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CreditCardService {
    ResponseEntity<String> createCreditCard(Long accountId);

    ResponseEntity<List<CreditCardWrapper>> getCreditCard(Long accountId);

    ResponseEntity<String> reqCreditCard(Long accountId);

    ResponseEntity<String> deleteRequestCreditCard(Long requestId);

    ResponseEntity<List<SendCreditCardReqWrapper>> getAllCreditCardsRequests();
}
