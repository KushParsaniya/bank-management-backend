package dev.kush.backend.backend.repository;

import dev.kush.backend.backend.models.features.CreditCard;
import dev.kush.backend.backend.models.features.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard,Long> {
    Optional<DebitCard> findByCardNumber(String cardNumber);
}
