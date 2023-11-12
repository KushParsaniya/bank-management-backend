package dev.kush.backend.backend.cards.debitCards.service;

import dev.kush.backend.backend.cards.debitCards.model.DebitCardWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DebitCardService {
    ResponseEntity<String> createDebitCard(Long accountId);

    ResponseEntity<List<DebitCardWrapper>> getDebitCard(Long accountId);
}
