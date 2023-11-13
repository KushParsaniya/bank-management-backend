package dev.kush.backend.backend.cards.debitCards.controller;


import dev.kush.backend.backend.cards.debitCards.model.DebitCardWrapper;
import dev.kush.backend.backend.cards.debitCards.service.DebitCardService;
import dev.kush.backend.backend.cards.debitCards.service.DebitCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account/info/cards/")
public class DebitCardController {

    private final DebitCardService debitCardService;

    @Autowired
    public DebitCardController(DebitCardServiceImpl debitCardService) {
        this.debitCardService = debitCardService;
    }


    @GetMapping("createDebitCard/{accountId}")
    public ResponseEntity<String> createDebitCard(@PathVariable Long accountId){
        return debitCardService.createDebitCard(accountId);
    }

    @GetMapping("getDebitCard/{accountId}")
    public ResponseEntity<List<DebitCardWrapper>> getDebitCard(@PathVariable Long accountId){
        return debitCardService.getDebitCard(accountId);
    }
}
