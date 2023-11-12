package dev.kush.backend.backend.customer.repository;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Account> {
    Optional<Customer> findCustomerByEmail(String email);
}
