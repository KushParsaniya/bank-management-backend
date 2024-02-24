package dev.kush.backend.customer.service.impl;

import dev.kush.backend.account.models.Account;
import dev.kush.backend.account.models.Transaction;
import dev.kush.backend.account.models.TransactionWrapper;
import dev.kush.backend.account.repository.AccountRepository;
import dev.kush.backend.account.repository.TransactionRepository;
import dev.kush.backend.customer.dto.LoginCustomerDto;
import dev.kush.backend.customer.dto.SendDetailDto;
import dev.kush.backend.customer.dto.SendDetailDtoWithJwt;
import dev.kush.backend.customer.dto.SignUpDetailDto;
import dev.kush.backend.customer.model.*;
import dev.kush.backend.customer.repository.CustomerRepository;
import dev.kush.backend.customer.service.ConformationTokenService;
import dev.kush.backend.customer.service.CustomerService;
import dev.kush.backend.customer.service.EmailService;
import dev.kush.backend.customer.service.SecuredCustomerService;
import dev.kush.backend.exception.ConflictException;
import dev.kush.backend.exception.UnauthorizedUserException;
import dev.kush.backend.exception.UserNotFoundException;
import dev.kush.backend.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Value("${base.url}")
    private String URL;

    private final CustomerRepository customerRepository;
    private final SecuredCustomerService securedCustomerService;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ConformationTokenService conformationTokenService;
    private final EmailService emailService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, SecuredCustomerService securedCustomerService, AccountRepository accountRepository, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, ConformationTokenService conformationTokenService, EmailService emailService) {
        this.customerRepository = customerRepository;
        this.securedCustomerService = securedCustomerService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.conformationTokenService = conformationTokenService;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<SendDetailDtoWithJwt> login(LoginCustomerDto loginCustomer) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginCustomer.getEmail(),
                            loginCustomer.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new UnauthorizedUserException("Wrong credentials");
        }

        UserDetails userDetails = securedCustomerService.loadUserByUsername(loginCustomer.getEmail());

        String token = jwtUtils.generateToken(userDetails);


        Customer customer = customerRepository.findCustomerByEmail(loginCustomer.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Customer with " + loginCustomer.getEmail() + " does not exist")

        );


        // get account associated with customer
        Account account = accountRepository.findReferenceByCustomerId(customer.getId()).orElseThrow(
                () -> new UserNotFoundException("No account found for customerId " + customer.getId())
        );

        // all transaction associated with the account
        List<Transaction> transactions = transactionRepository.findAllReferenceByAccountId(account.getId()).orElse(List.of());

        // now convert this transaction to transactionWrapper
        List<TransactionWrapper> transactionWrappers = transactions.stream().map(transaction ->
                new TransactionWrapper(transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getAmount())).toList();

        SendDetailDto sendDetailDto = new SendDetailDto(
                customer.getName(),
                customer.getEmail(),
                account.getId(),
                account.getBalance(),
                account.getAccountType(),
                customer.getRole(),
                transactionWrappers
        );

        return new ResponseEntity<>(new SendDetailDtoWithJwt(sendDetailDto,token), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> create(SignUpDetailDto signUpDetailDto) {

        // check if email already exist in our database

        if (customerRepository.existsByEmail(signUpDetailDto.getEmail())) {
            throw new ConflictException("user with email " + signUpDetailDto.getEmail() + " is already exist.");
        }

        Customer customer = new Customer(
                signUpDetailDto.getUsername(),
                signUpDetailDto.getEmail(),
                passwordEncoder.encode(signUpDetailDto.getPassword()),
                "ROLE_USER",
                false,
                false
        );

        Account account = new Account(
                0L, signUpDetailDto.getAccountType(), customer
        );
        customer.setAccount(account);

        customerRepository.save(customer);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                customer
        );

        conformationTokenService.saveToken(confirmationToken);
        // send verification email
        String link = "https://" + URL + "/confirm?token=" + token;

        String message = emailService.sendMail(new EmailDetails(
                signUpDetailDto.getEmail(),
                String.format("<p>Hello %s  your verification link is here click here to " +
                        "verify your email : </p>",customer.getName()) +
                        "<a href=\""
                        + link + "\">Activate Now</a>",
                "Verification mail"
        ));
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteCustomer(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
        } else {
            throw new UserNotFoundException("Customer not found.");
        }
    }

    @Override
    public void enableAccount(String email) {
        customerRepository.updateEnabledByEmail(email);
    }

    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = conformationTokenService.getToken(token).orElseThrow(
                () -> new UserNotFoundException("token does not exist.")
        );

        if (confirmationToken.getConfirmedAt() != null){
            return "token already confirmed";
        }

        if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())){
            return "token expired";
        }

        conformationTokenService.setConfirmedAt(token);
        enableAccount(confirmationToken.getCustomer().getEmail());
        return "successfully verified.";
    }
}
