package dev.kush.backend.backend.configurations;

import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.customer.model.Role;
import dev.kush.backend.backend.loans.model.LoanType;
import dev.kush.backend.backend.cards.creditCards.model.CreditCard;
import dev.kush.backend.backend.customer.model.Customer;
import dev.kush.backend.backend.account.models.AccountType;
import dev.kush.backend.backend.cards.debitCards.model.DebitCard;
import dev.kush.backend.backend.loans.model.Loan;
import dev.kush.backend.backend.transactions.model.Transaction;
import dev.kush.backend.backend.account.repository.AccountRepository;
import dev.kush.backend.backend.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static dev.kush.backend.backend.customer.model.Role.ADMIN;
import static dev.kush.backend.backend.transactions.model.TransactionType.*;

@Configuration
public class SampleData {
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, AccountRepository accountRepository){
        return args -> {
            Customer customer = new Customer();
            customer.setUserName("Kush Parsaniya");
            customer.setEmail("kush@gmail.com");
            customer.setPassword("kush1234");
            customer.setRole(ADMIN);
            Account account = new Account(10000L, AccountType.SAVING,customer);

            customer.setAccount(account);

            CreditCard creditCard1 = new CreditCard("1234","123",25000L,23000L,"2023-11-01",account);
            CreditCard creditCard2 = new CreditCard("8945","646",20000L,19000L,"2023-11-10",account);

            DebitCard debitCard1 = new DebitCard("8945","143","2025-06-18",account);
            DebitCard debitCard2 = new DebitCard("5789","158","2025-01-24",account);

            Loan loan1 = new Loan(LoanType.HOME,100000L,3.5F,account);
            Loan loan2 = new Loan(LoanType.PERSONAL,10000L,2.5F,account);

            Transaction t1 = new Transaction("2023-11-01","12:56:08.830293600" , DEPOSIT,2000L,account);
            Transaction t2 = new Transaction("2023-10-27","9:36:01.188119",WITHDRAW,5000L,account);
            Transaction t3 = new Transaction("2023-10-15","21:12:02.753475",INTEREST,200L,account);
            Transaction t4 = new Transaction("2023-09-29","18:53:02.7534",TRANSFER,500L,account);

            account.setCreditCards(List.of(creditCard1,creditCard2));
            account.setDebitCards(List.of(debitCard1,debitCard2));
            account.setLoans(List.of(loan1,loan2));
            account.setTransactions(List.of(t1,t2,t3,t4));


            accountRepository.save(account);
            customerRepository.save(customer);
        };
    }
}
