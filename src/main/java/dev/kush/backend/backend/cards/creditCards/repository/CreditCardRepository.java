package dev.kush.backend.backend.cards.creditCards.repository;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.cards.creditCards.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
    Optional<CreditCard> findByCardNumber(String cardNumber);
    Optional<List<CreditCard>> findAllReferenceByAccountId(Long accountId);


}
