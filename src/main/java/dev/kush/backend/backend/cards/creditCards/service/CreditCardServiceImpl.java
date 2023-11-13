package dev.kush.backend.backend.cards.creditCards.service;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.cards.creditCards.model.CreditCard;
import dev.kush.backend.backend.cards.creditCards.model.CreditCardWrapper;
import dev.kush.backend.backend.account.repository.AccountRepository;
import dev.kush.backend.backend.cards.creditCards.repository.CreditCardRepository;
import dev.kush.backend.backend.cards.service.CardGeneratorService;
import dev.kush.backend.backend.cards.service.CardGeneratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService{

    private final CardGeneratorService cardGeneratorService;
    private final CreditCardRepository creditCardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CreditCardServiceImpl(CardGeneratorServiceImpl cardGeneratorService, CreditCardRepository creditCardRepository, AccountRepository accountRepository) {
        this.cardGeneratorService = cardGeneratorService;
        this.creditCardRepository = creditCardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity<String> createCreditCard(Long accountId) {
        try {
            Account account = accountRepository.findById(accountId).orElse(null);

            if (account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            }

            String cardNumber = cardGeneratorService.generateCardNumber();
            CreditCard cardOptional = creditCardRepository.findByCardNumber(cardNumber).orElse(null);

            // check if card with cardNumber exists
            while(cardOptional != null){
                cardNumber = cardGeneratorService.generateCardNumber();
                cardOptional = creditCardRepository.findByCardNumber(cardNumber).orElse(null);
            }


            CreditCard creditCard = new CreditCard(
                    cardNumber,
                    cardGeneratorService.generateCVV(),
                    25000L,
                    25000L,
                    cardGeneratorService.generateExpirationDate(),
                    account
            );
            account.setCreditCards(List.of(creditCard));
            creditCardRepository.save(creditCard);
            return new ResponseEntity<>("succesfully created",HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CreditCardWrapper>> getCreditCard(Long accountId) {
        try {
            List<CreditCard> creditCards = creditCardRepository.findAllReferenceByAccountId(accountId).orElse(List.of());

            if (creditCards.isEmpty()) {
                return new ResponseEntity<>(List.of(), HttpStatus.OK);
            }

            List<CreditCardWrapper> creditCardWrappers = new ArrayList<>();

            for(CreditCard creditCard : creditCards){
                creditCardWrappers.add(new CreditCardWrapper(
                        // used this to format credit card number
                        creditCard.getCardNumber().replaceAll("(.{4})", "$1 "),
                        creditCard.getExpirationDate().substring(0,7),
                        creditCard.getCreditLimit(),
                        creditCard.getCreditLimit() - creditCard.getRemainingLimit(),
                        creditCard.getCvv()
                ));
            }
            return new ResponseEntity<>(creditCardWrappers,HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(List.of(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
