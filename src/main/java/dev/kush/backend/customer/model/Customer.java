package dev.kush.backend.customer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.kush.backend.account.models.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static jakarta.persistence.GenerationType.IDENTITY;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Size(min = 2 , message = "username should have at least 2 characters.")
    private String userName;
    @Email(message = "Invalid email address")
    private String email;
    @Size(min = 4 , message = "password should have at least 4 characters")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonBackReference
    private Account account;

    @Transient
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);


    public Customer(String userName, String email, String password, Role role) {
        this.userName = userName;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.role = role;
    }

    public Customer(String userName, String email, String password, Role role, Account account) {
        this.userName = userName;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.role = role;
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }
}
