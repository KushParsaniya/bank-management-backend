package dev.kush.backend.account.repository;

import dev.kush.backend.account.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Optional<List<Transaction>> findAllReferenceByAccountId(Long id);

}
