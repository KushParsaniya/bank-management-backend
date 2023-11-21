package dev.kush.backend.loans.repository;

import dev.kush.backend.loans.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    Optional<List<Loan>> findAllReferenceByAccountId(Long accountId);

}
