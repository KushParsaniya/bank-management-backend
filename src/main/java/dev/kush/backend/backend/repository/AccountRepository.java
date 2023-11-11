package dev.kush.backend.backend.repository;

import dev.kush.backend.backend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findReferenceByCustomerId(Long id);
}
