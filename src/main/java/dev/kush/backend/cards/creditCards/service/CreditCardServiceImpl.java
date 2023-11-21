package dev.kush.backend.cards.creditCards.service;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.cards.creditCards.model.CreditCard;
import dev.kush.backend.cards.creditCards.model.CreditCardRequest;
import dev.kush.backend.cards.creditCards.model.CreditCardWrapper;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.cards.creditCards.model.SendCreditCardReqWrapper;
import dev.kush.backend.cards.creditCards.repository.CreditCardRepository;
import dev.kush.backend.cards.creditCards.repository.CreditCardRequestRepository;
import dev.kush.backend.cards.service.CardGeneratorService;
import dev.kush.backend.cards.service.CardGeneratorServiceImpl;
import dev.kush.backend.customer.model.Customer;
import dev.kush.backend.exception.UserNotFoundException;
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
            Account account = accountRepository.findById(accountId).orElseThrow(
                    () -> new UserNotFoundException("Account with id " + accountId + " does not exist")
            );


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
    }

    // admin can generate credit card
    @Override
    public ResponseEntity<List<CreditCardWrapper>> getCreditCard(Long accountId) {

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
    }

    // user can req credit card
    @Override
    public ResponseEntity<String> reqCreditCard(Long accountId) {

            Account account = accountRepository.findById(accountId).orElseThrow(
                    () -> new UserNotFoundException("Account with id " + accountId + " does not exist")
            );


            CreditCardRequest cardRequest = new CreditCardRequest(25000L,account);
            account.setCreditCardRequests(List.of(cardRequest));

            creditCardRequestRepository.save(cardRequest);
            return new ResponseEntity<>("Successfully applied",HttpStatus.OK);

    }


    @Override
    public ResponseEntity<String> deleteRequestCreditCard(Long requestId) {

            CreditCardRequest cardRequest = creditCardRequestRepository.findById(requestId).orElseThrow(
                    () -> new UserNotFoundException("credit card request not found")
            );


            creditCardRequestRepository.delete(cardRequest);
            return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SendCreditCardReqWrapper>> getAllCreditCardsRequests() {

            List<CreditCardRequest> creditCardRequests = creditCardRequestRepository.findAll();

            if (creditCardRequests.isEmpty()) {
                return new ResponseEntity<>(List.of(), HttpStatus.OK);
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

    }

    // after admin approve req it will delete the request from the table

    // TODO : Give all status of all pending request to USER

}
