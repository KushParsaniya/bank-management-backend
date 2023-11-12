package dev.kush.backend.backend.account.repository;

import dev.kush.backend.backend.account.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findReferenceByCustomerId(Long id);
}
