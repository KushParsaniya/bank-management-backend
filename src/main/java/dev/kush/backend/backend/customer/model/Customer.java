package dev.kush.backend.backend.customer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.kush.backend.backend.account.models.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String userName;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonBackReference
    private Account account;


    public Customer(String userName, String email, String password, Role role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Customer(String userName, String email, String password, Role role, Account account) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.account = account;
    }
}
