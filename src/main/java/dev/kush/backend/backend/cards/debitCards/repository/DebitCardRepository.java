package dev.kush.backend.backend.cards.debitCards.repository;

import dev.kush.backend.backend.cards.debitCards.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard,Long> {
    Optional<DebitCard> findByCardNumber(String cardNumber);
    Optional<List<DebitCard>> findAllReferenceByAccountId(Long accountId);

}
