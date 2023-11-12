package dev.kush.backend.backend.services.cards.debitCard;

import dev.kush.backend.backend.models.wrapper.DebitCardWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DebitCardService {
    ResponseEntity<String> createDebitCard(Long accountId);

    ResponseEntity<List<DebitCardWrapper>> getDebitCard(Long accountId);
}
