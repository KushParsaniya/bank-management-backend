package dev.kush.backend.backend.configurations;

import dev.kush.backend.backend.models.Account;
import dev.kush.backend.backend.models.Customer;
import dev.kush.backend.backend.models.enums.AccountType;
import dev.kush.backend.backend.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleData {
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return args -> {
            Customer customer = new Customer();
            customer.setUserName("Kush Parsaniya");
            customer.setEmail("kush@gmail.com");
            customer.setPassword("kush1234");

            Account account = new Account(10000L, AccountType.SAVING,customer);

            customer.setAccount(account);

            customerRepository.save(customer);
        };
    }
}
