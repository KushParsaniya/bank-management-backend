package dev.kush.backend.backend.cards.creditCards.service;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.cards.creditCards.model.CreditCard;
import dev.kush.backend.backend.cards.creditCards.model.CreditCardRequest;
import dev.kush.backend.backend.cards.creditCards.model.CreditCardWrapper;
import dev.kush.backend.backend.account.repository.AccountRepository;
import dev.kush.backend.backend.cards.creditCards.model.SendCreditCardReqWrapper;
import dev.kush.backend.backend.cards.creditCards.repository.CreditCardRepository;
import dev.kush.backend.backend.cards.creditCards.repository.CreditCardRequestRepository;
import dev.kush.backend.backend.cards.service.CardGeneratorService;
import dev.kush.backend.backend.cards.service.CardGeneratorServiceImpl;
import dev.kush.backend.backend.customer.model.Customer;
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
    private final CreditCardRequestRepository creditCardRequestRepository;

    @Autowired
    public CreditCardServiceImpl(CardGeneratorService cardGeneratorService, CreditCardRepository creditCardRepository, AccountRepository accountRepository, CreditCardRequestRepository creditCardRequestRepository) {
        this.cardGeneratorService = cardGeneratorService;
        this.creditCardRepository = creditCardRepository;
        this.accountRepository = accountRepository;
        this.creditCardRequestRepository = creditCardRequestRepository;
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

    // admin can generate credit card
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

    // user can req credit card
    @Override
    public ResponseEntity<String> reqCreditCard(Long accountId) {
        try {
            Account account = accountRepository.findById(accountId).orElse(null);

            if (account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            }

            CreditCardRequest cardRequest = new CreditCardRequest(25000L,account);
            account.setCreditCardRequests(List.of(cardRequest));

            creditCardRequestRepository.save(cardRequest);
            return new ResponseEntity<>("Successfully applied",HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> deleteRequestCreditCard(Long requestId) {
        try {
            CreditCardRequest cardRequest = creditCardRequestRepository.findById(requestId).orElse(null);

            if (cardRequest == null) {
                return new ResponseEntity<>("request not found",HttpStatus.NOT_FOUND);
            }
            creditCardRequestRepository.delete(cardRequest);
            return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<SendCreditCardReqWrapper>> getAllCreditCardsRequests() {
        try {
            List<CreditCardRequest> creditCardRequests = creditCardRequestRepository.findAll();

            if (creditCardRequests.isEmpty()) {
                return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
            }

            List<SendCreditCardReqWrapper> sendCreditCardReqWrappers = new ArrayList<>();


            // we want to send this info to admins
            for (CreditCardRequest creditCardRequest : creditCardRequests) {

                Account account = creditCardRequest.getAccount();

                if (account == null) {
                    continue;
                }
                Customer customer = account.getCustomer();

                if (customer == null) {
                    continue;
                }

                sendCreditCardReqWrappers.add(new SendCreditCardReqWrapper(
                        customer.getUserName(),
                        customer.getEmail(),
                        account.getId(),
                        account.getBalance(),
                        creditCardRequest.getId(),
                        creditCardRequest.getAppliedAmount()));
            }
            return new ResponseEntity<>(sendCreditCardReqWrappers,HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(List.of(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // after admin approve req it will delete the request from the table

}
