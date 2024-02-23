package dev.kush.backend.configurations.security;

import dev.kush.backend.customer.service.SecuredCustomerService;
import dev.kush.backend.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecuredCustomerService securedCustomerService;
    private final JwtFilter jwtFilter;

    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfiguration(SecuredCustomerService securedCustomerService, JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.securedCustomerService = securedCustomerService;
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
//                auth -> auth.anyRequest().permitAll()

                        auth -> auth.
                                requestMatchers(
                                        "/account/info/cards/createCreditCard/**",
                                        "/account/info/cards/deleteRequestCreditCard/**",
                                        "/account/info/cards/getAllCreditCardsRequests",
                                        "/account/info/cards/createDebitCard/**",
                                        "/account/info/cards/deleteRequestDebitCard/*8",
                                        "/account/info/cards/getAllDebitCardsRequests",
                                        "/account/info/loans/createLoan/**",
                                        "/account/info/loans/deleteLoanRequest/**",
                                        "/account/info/loans/getAllLoanRequests",
                                        "account/info/transfer",
                                        "account/info/deposit"
                                        ).hasRole("ADMIN")
                                .requestMatchers("/create", "/delete","/login","/signin").permitAll()
                                .anyRequest().hasAnyRole("ADMIN","USER")
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .cors(cors -> cors.configurationSource(
                        request -> corsConfiguration()
                ))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
        ;


        return http.build();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("https://easybankdev.vercel.app"));
        cors.setMaxAge(3600L);
        cors.setAllowedMethods(List.of("*"));
        cors.setExposedHeaders(List.of("Authorization"));
        cors.setAllowCredentials(true);

        return cors;
    }


}
