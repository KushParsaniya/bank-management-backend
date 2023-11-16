package dev.kush.backend.backend.loans.repository;

import dev.kush.backend.backend.loans.model.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest,Long> {
}
