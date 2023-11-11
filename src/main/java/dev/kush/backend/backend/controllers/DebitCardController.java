package dev.kush.backend.backend.controllers;


import dev.kush.backend.backend.services.cards.debitCard.DebitCardService;
import dev.kush.backend.backend.services.cards.debitCard.DebitCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
