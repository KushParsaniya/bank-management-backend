package dev.kush.backend.backend.controllers;


import dev.kush.backend.backend.models.wrapper.DebitCardWrapper;
import dev.kush.backend.backend.services.cards.debitCard.DebitCardService;
import dev.kush.backend.backend.services.cards.debitCard.DebitCardServiceImpl;
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

    @CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
    @GetMapping("createDebitCard/{accountId}")
    public ResponseEntity<String> createDebitCard(@PathVariable Long accountId){
        return debitCardService.createDebitCard(accountId);
    }

    @CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
    @GetMapping("getDebitCard/{accountId}")
    public ResponseEntity<List<DebitCardWrapper>> getDebitCard(@PathVariable Long accountId){
        return debitCardService.getDebitCard(accountId);
    }
}
