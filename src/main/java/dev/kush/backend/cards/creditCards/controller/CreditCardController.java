package dev.kush.backend.cards.creditCards.controller;

import dev.kush.backend.cards.creditCards.model.CreditCardWrapper;
import dev.kush.backend.cards.creditCards.model.SendCreditCardReqWrapper;
import dev.kush.backend.cards.creditCards.service.CreditCardService;
import dev.kush.backend.cards.creditCards.service.CreditCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/requestCreditCard/{accountId}")
    public ResponseEntity<String> reqCreditCard(@PathVariable Long accountId){
        return creditCardService.reqCreditCard(accountId);
    }

//    @CrossOrigin(origins = "http://localhost:3000/",allowedHeaders = "*")
    @DeleteMapping("/deleteRequestCreditCard/{requestId}")
    public ResponseEntity<String> deleteRequestCreditCard(@PathVariable Long requestId){
        return creditCardService.deleteRequestCreditCard(requestId);
    }

    @GetMapping("/getAllCreditCardsRequests")
    public ResponseEntity<List<SendCreditCardReqWrapper>> getAllCreditCardsRequests(){
        return creditCardService.getAllCreditCardsRequests();
    }

}
