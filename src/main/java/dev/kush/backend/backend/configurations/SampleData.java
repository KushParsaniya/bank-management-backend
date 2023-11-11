package dev.kush.backend.backend.configurations;

import dev.kush.backend.backend.models.Account;
import dev.kush.backend.backend.models.features.CreditCard;
import dev.kush.backend.backend.models.Customer;
import dev.kush.backend.backend.models.enums.AccountType;
import dev.kush.backend.backend.models.features.DebitCard;
import dev.kush.backend.backend.repository.AccountRepository;
import dev.kush.backend.backend.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SampleData {
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, AccountRepository accountRepository){
        return args -> {
            Customer customer = new Customer();
            customer.setUserName("Kush Parsaniya");
            customer.setEmail("kush@gmail.com");
            customer.setPassword("kush1234");

            Account account = new Account(10000L, AccountType.SAVING,customer);

            customer.setAccount(account);

            CreditCard creditCard1 = new CreditCard("1234","123",25000L,23000L,"2023-11-01",account);
            CreditCard creditCard2 = new CreditCard("8945","646",20000L,19000L,"2023-11-10",account);

            DebitCard debitCard1 = new DebitCard("8945","143","2025-06-18",account);
            DebitCard debitCard2 = new DebitCard("5789","158","2025-01-24",account);

            account.setCreditCards(List.of(creditCard1,creditCard2));
            account.setDebitCards(List.of(debitCard1,debitCard2));


            accountRepository.save(account);
            customerRepository.save(customer);
        };
    }
}
