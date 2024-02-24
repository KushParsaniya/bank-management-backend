package dev.kush.backend.customer.repository;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByEmail(String email);

    @Query("select (count(a) > 0) from Customer a where a.email = ?1")
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Customer a set a.enabled = TRUE where a.email = ?1")
    void updateEnabledByEmail(String email);
}
