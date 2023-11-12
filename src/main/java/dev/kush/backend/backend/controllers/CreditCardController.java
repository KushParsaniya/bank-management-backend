package dev.kush.backend.backend.controllers;

import dev.kush.backend.backend.models.wrapper.CreditCardWrapper;
import dev.kush.backend.backend.services.cards.creditCard.CreditCardService;
import dev.kush.backend.backend.services.cards.creditCard.CreditCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account/info/cards/")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardServiceImpl creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("createCreditCard/{accountId}")
    public ResponseEntity<String> createCreditCard(@PathVariable Long accountId){
        return creditCardService.createCreditCard(accountId);
    }

    @GetMapping("getCreditCard/{accountId}")
    public ResponseEntity<List<CreditCardWrapper>> getCreditCard(@PathVariable Long accountId){
        return creditCardService.getCreditCard(accountId);
    }
}
