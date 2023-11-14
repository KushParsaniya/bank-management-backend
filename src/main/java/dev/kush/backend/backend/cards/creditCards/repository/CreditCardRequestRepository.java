package dev.kush.backend.backend.cards.creditCards.repository;

import dev.kush.backend.backend.cards.creditCards.model.CreditCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRequestRepository extends JpaRepository<CreditCardRequest,Long> {
}
