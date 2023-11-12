package dev.kush.backend.backend.services.cards.debitCard;

import dev.kush.backend.backend.models.Account;
import dev.kush.backend.backend.models.features.CreditCard;
import dev.kush.backend.backend.models.features.DebitCard;
import dev.kush.backend.backend.models.wrapper.CreditCardWrapper;
import dev.kush.backend.backend.models.wrapper.DebitCardWrapper;
import dev.kush.backend.backend.repository.AccountRepository;
import dev.kush.backend.backend.repository.CreditCardRepository;
import dev.kush.backend.backend.repository.DebitCardRepository;
import dev.kush.backend.backend.services.cards.CardGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DebitCardServiceImpl implements DebitCardService{


    private final CardGeneratorService cardGeneratorService;
    private final DebitCardRepository debitCardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public DebitCardServiceImpl(CardGeneratorService cardGeneratorService, DebitCardRepository debitCardRepository, AccountRepository accountRepository) {
        this.cardGeneratorService = cardGeneratorService;
        this.debitCardRepository = debitCardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity<String> createDebitCard(Long accountId) {
        try {
            Account account = accountRepository.findById(accountId).orElse(null);

            if (account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            }

            String cardNumber = cardGeneratorService.generateCardNumber();
            DebitCard cardOptional = debitCardRepository.findByCardNumber(cardNumber).orElse(null);

            // check if card with cardNumber exists
            while(cardOptional != null){
                cardNumber = cardGeneratorService.generateCardNumber();
                cardOptional = debitCardRepository.findByCardNumber(cardNumber).orElse(null);
            }

            DebitCard debitCard = new DebitCard(
                    cardNumber,
                    cardGeneratorService.generateCVV(),
                    cardGeneratorService.generateExpirationDate(),
                    account
            );
            account.setDebitCards(List.of(debitCard));
            debitCardRepository.save(debitCard);
            return new ResponseEntity<>("succesfully created",HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<DebitCardWrapper>> getDebitCard(Long accountId) {
        try {
            List<DebitCard> debitCards = debitCardRepository.findAllReferenceByAccountId(accountId).orElse(List.of());

            if (debitCards.isEmpty()) {
                return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
            }

            List<DebitCardWrapper> debitCardWrappers = new ArrayList<>();

            for(DebitCard debitCard : debitCards){
                debitCardWrappers.add(
                        new DebitCardWrapper(
                                debitCard.getCardNumber(),
                                debitCard.getExpirationDate(),
                                debitCard.getCvv()
                        )
                );
            }
            return new ResponseEntity<>(debitCardWrappers,HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(List.of(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
