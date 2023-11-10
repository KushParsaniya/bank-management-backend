package dev.kush.backend.backend.repository;

import dev.kush.backend.backend.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
}
