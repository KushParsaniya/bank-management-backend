package dev.kush.backend.configurations;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.account.models.AccountType;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.cards.creditCards.model.CreditCard;
import dev.kush.backend.customer.model.Customer;
import dev.kush.backend.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static dev.kush.backend.customer.model.Role.ADMIN;
import static dev.kush.backend.customer.model.Role.USER;

@Configuration
public class SampleData {
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, AccountRepository accountRepository){
        return args -> {
            Customer customer1 = new Customer("Kush Parsaniya","kush@gmail.com","kush1234", ADMIN);
            Account account1 = new Account(10000L, AccountType.ADMIN,customer1);
            customer1.setAccount(account1);

            Customer customer2 = new Customer("Abhi Parsaniya","abhi@gmail.com","abhi1234", USER);
            Account account2 = new Account(5000L, AccountType.SAVING,customer2);
            customer2.setAccount(account2);

            CreditCard creditCard1 = new CreditCard("1234464727123465","123",25000L,23000L, LocalDate.now().plusYears(3).toString(),account1);
            CreditCard creditCard2 = new CreditCard("8588949832285738","198",25000L,22000L,"2025-09-11",account2);

            account1.setCreditCards(List.of(creditCard1));
            account2.setCreditCards(List.of(creditCard2));


            customerRepository.saveAll(List.of(customer1, customer2));


        };
    }
}
