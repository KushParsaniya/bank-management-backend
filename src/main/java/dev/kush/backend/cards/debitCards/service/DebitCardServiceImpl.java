package dev.kush.backend.cards.debitCards.service;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.cards.creditCards.model.CreditCardRequest;
import dev.kush.backend.cards.creditCards.model.SendCreditCardReqWrapper;
import dev.kush.backend.cards.debitCards.model.DebitCard;
import dev.kush.backend.cards.debitCards.model.DebitCardRequest;
import dev.kush.backend.cards.debitCards.model.DebitCardWrapper;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.cards.debitCards.model.SendDebitCardReqWrapper;
import dev.kush.backend.cards.debitCards.repository.DebitCardRepository;
import dev.kush.backend.cards.debitCards.repository.DebitCardRequestRepository;
import dev.kush.backend.cards.service.CardGeneratorService;
import dev.kush.backend.customer.model.Customer;
import dev.kush.backend.exception.UserNotFoundException;
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
            Account account = accountRepository.findById(accountId).orElseThrow(
                    () -> new UserNotFoundException("Account with id " + accountId + " does not exist")

            );

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

    }

    @Override
    public ResponseEntity<List<DebitCardWrapper>> getDebitCard(Long accountId) {

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

    }

    //user request debit card

    @Override
    public ResponseEntity<String> requestDebitCard(Long accountId) {
            Account account = accountRepository.findById(accountId).orElseThrow(
                    () -> new UserNotFoundException("Account with id " + accountId + " does not exist")

            );

            DebitCardRequest cardRequest = new DebitCardRequest(account);
            account.setDebitCardRequests(List.of(cardRequest));

            debitCardRequestRepository.save(cardRequest);
            return new ResponseEntity<>("Successfully applied", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteReqDebitCard(Long requestId) {
            DebitCardRequest cardRequest = debitCardRequestRepository.findById(requestId).orElseThrow(
                    () -> new UserNotFoundException("debit card request not found")
            );

            debitCardRequestRepository.delete(cardRequest);
            return new ResponseEntity<>("successfully deleted",HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<SendDebitCardReqWrapper>> getAllReqDebitCards() {
            List<DebitCardRequest> debitCardRequests = debitCardRequestRepository.findAll();

            if (debitCardRequests.isEmpty()) {
                return new ResponseEntity<>(List.of(), HttpStatus.OK);
            }

            List<SendDebitCardReqWrapper> sendDebitCardReqWrappers = new ArrayList<>();


            // we want to send this info to admins
            for (DebitCardRequest debitCardRequest : debitCardRequests) {

                Account account = debitCardRequest.getAccount();

                if (account == null) {
                    continue;
                }
                Customer customer = account.getCustomer();

                if (customer == null) {
                    continue;
                }

                sendDebitCardReqWrappers.add(new SendDebitCardReqWrapper(
                        customer.getUserName(),
                        customer.getEmail(),
                        account.getId(),
                        account.getBalance(),
                        debitCardRequest.getId()));
            }
            return new ResponseEntity<>(sendDebitCardReqWrappers,HttpStatus.OK);


    }


}


