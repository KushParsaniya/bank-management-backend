package dev.kush.backend.configurations.security;

import dev.kush.backend.customer.model.SecuredCustomer;
import dev.kush.backend.customer.service.SecuredCustomerService;
import dev.kush.backend.customer.service.SecuredCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private SecuredCustomerService securedCustomerService;

    @Autowired
    public SecurityConfiguration(SecuredCustomerServiceImpl securedCustomerService) {
        this.securedCustomerService = securedCustomerService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
//                auth -> auth.anyRequest().permitAll()
                auth -> auth.anyRequest().authenticated()
        )
//                .cors(cors -> {})
        .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
