package dev.kush.backend.cards.creditCards.repository;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.cards.creditCards.model.CreditCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRequestRepository extends JpaRepository<CreditCardRequest,Long> {
}
