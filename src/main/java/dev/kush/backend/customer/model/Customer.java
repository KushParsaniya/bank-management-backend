package dev.kush.backend.customer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.kush.backend.account.models.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Size(min = 2 , message = "username should have at least 2 characters.")
    private java.lang.String name;
    @Email(message = "Invalid email address")
    private java.lang.String email;
    @Size(min = 4 , message = "password should have at least 4 characters")
    private java.lang.String password;

    private java.lang.String role;

    private boolean locked;

    private boolean enabled;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonBackReference
    private Account account;


    public Customer(java.lang.String name, java.lang.String email, java.lang.String password, java.lang.String role, boolean locked, boolean enabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.enabled = enabled;
    }

    public Customer(java.lang.String name, java.lang.String email, java.lang.String password, java.lang.String role, boolean locked, boolean enabled, Account account) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.enabled = enabled;
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public java.lang.String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
