package dev.kush.backend.backend.services.cards.creditCard;

import dev.kush.backend.backend.models.wrapper.CreditCardWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CreditCardService {
    ResponseEntity<String> createCreditCard(Long accountId);

    ResponseEntity<List<CreditCardWrapper>> getCreditCard(Long accountId);
}
