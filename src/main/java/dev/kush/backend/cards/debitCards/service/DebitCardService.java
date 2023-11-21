package dev.kush.backend.cards.debitCards.service;

import dev.kush.backend.cards.debitCards.model.DebitCardWrapper;
import dev.kush.backend.cards.debitCards.model.SendDebitCardReqWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DebitCardService {
    ResponseEntity<String> createDebitCard(Long accountId);

    ResponseEntity<List<DebitCardWrapper>> getDebitCard(Long accountId);

    ResponseEntity<String> requestDebitCard(Long accountId);

    ResponseEntity<String> deleteReqDebitCard(Long requestId);

    ResponseEntity<List<SendDebitCardReqWrapper>> getAllReqDebitCards();
}
