package dev.kush.backend.backend.cards.debitCards.repository;

import dev.kush.backend.backend.cards.debitCards.model.DebitCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRequestRepository extends JpaRepository<DebitCardRequest,Long> {
}
