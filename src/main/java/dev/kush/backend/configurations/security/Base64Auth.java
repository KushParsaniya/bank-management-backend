package dev.kush.backend.configurations.security;

import dev.kush.backend.customer.service.SecuredCustomerService;
import dev.kush.backend.customer.service.SecuredCustomerServiceImpl;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class Base64Auth extends UsernamePasswordAuthenticationFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private SecuredCustomerService securedCustomerService; // Holds the expected user details

    public Base64Auth(SecuredCustomerService securedCustomerService, AuthenticationManager authenticationManager) {
        this.securedCustomerService = securedCustomerService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
    }
}
