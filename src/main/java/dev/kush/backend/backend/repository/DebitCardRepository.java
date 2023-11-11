package dev.kush.backend.backend.repository;

import dev.kush.backend.backend.models.features.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends JpaRepository<CreditCard,Long> {
}
