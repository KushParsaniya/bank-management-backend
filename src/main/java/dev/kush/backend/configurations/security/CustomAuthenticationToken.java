package dev.kush.backend.configurations.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public CustomAuthenticationToken(UserDetails userDetails) {
        super(userDetails,userDetails.getPassword(),userDetails.getAuthorities());

    }
}
