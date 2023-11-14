package dev.kush.backend.backend.cards.debitCards.service;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.cards.creditCards.model.CreditCardRequest;
import dev.kush.backend.backend.cards.debitCards.model.DebitCard;
import dev.kush.backend.backend.cards.debitCards.model.DebitCardRequest;
import dev.kush.backend.backend.cards.debitCards.model.DebitCardWrapper;
import dev.kush.backend.backend.account.repository.AccountRepository;
import dev.kush.backend.backend.cards.debitCards.repository.DebitCardRepository;
import dev.kush.backend.backend.cards.debitCards.repository.DebitCardRequestRepository;
import dev.kush.backend.backend.cards.service.CardGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DebitCardServiceImpl implements DebitCardService {

    private final CardGeneratorService cardGeneratorService;
    private final DebitCardRepository debitCardRepository;
    private final AccountRepository accountRepository;
    private final DebitCardRequestRepository debitCardRequestRepository;

    @Autowired
    public DebitCardServiceImpl(CardGeneratorService cardGeneratorService, DebitCardRepository debitCardRepository, AccountRepository accountRepository, DebitCardRequestRepository debitCardRequestRepository) {
        this.cardGeneratorService = cardGeneratorService;
        this.debitCardRepository = debitCardRepository;
        this.accountRepository = accountRepository;
        this.debitCardRequestRepository = debitCardRequestRepository;
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
            while (cardOptional != null) {
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
            return new ResponseEntity<>("succesfully created", HttpStatus.OK);

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
                return new ResponseEntity<>(List.of(), HttpStatus.OK);
            }

            List<DebitCardWrapper> debitCardWrappers = new ArrayList<>();

            for (DebitCard debitCard : debitCards) {
                debitCardWrappers.add(
                        new DebitCardWrapper(
                                debitCard.getCardNumber().replaceAll("(.{4})", "$1 "),
                                debitCard.getExpirationDate().substring(0, 7),
                                debitCard.getCvv()
                        )
                );
            }
            return new ResponseEntity<>(debitCardWrappers, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //user request debit card

    @Override
    public ResponseEntity<String> requestDebitCard(Long accountId) {
        try {
            Account account = accountRepository.findById(accountId).orElse(null);

            if (account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            }

            DebitCardRequest cardRequest = new DebitCardRequest(account);
            account.setDebitCardRequests(List.of(cardRequest));

            debitCardRequestRepository.save(cardRequest);
            return new ResponseEntity<>("Successfully applied", HttpStatus.OK);

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteReqDebitCard(Long requestId) {
        try {
            DebitCardRequest cardRequest = debitCardRequestRepository.findById(requestId).orElse(null);

            if (cardRequest == null) {
                return new ResponseEntity<>("request not found",HttpStatus.NOT_FOUND);
            }
            debitCardRequestRepository.delete(cardRequest);
            return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }



}


