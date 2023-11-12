package dev.kush.backend.backend.transactions.repository;

import dev.kush.backend.backend.transactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Optional<List<Transaction>> findAllReferenceByAccountId(Long id);

}
