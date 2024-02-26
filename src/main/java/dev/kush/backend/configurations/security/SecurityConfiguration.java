package dev.kush.backend.configurations.security;

import dev.kush.backend.customer.service.SecuredCustomerService;
import dev.kush.backend.jwt.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.time.LocalDateTime;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfiguration(JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
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
                                        "account/info/deposit",
                                        "/swagger-ui.html"
                                ).hasRole("ADMIN")
                                .requestMatchers("/create", "/delete", "/login", "/signin","/confirm").permitAll()
                                .anyRequest().hasAnyRole("ADMIN", "USER")
                )
                .exceptionHandling(exception ->
                        exception.accessDeniedHandler(accessDeniedHandler())
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
        cors.setAllowedOrigins(List.of("https://easybankdev.vercel.app","http://localhost:3000"));
        cors.setMaxAge(3600L);
        cors.setAllowedMethods(List.of("*"));
        cors.setExposedHeaders(List.of("Authorization"));
        cors.setAllowCredentials(true);

        return cors;
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            response.setContentType("application/json");

            String message = "{\"timestamp\":\"" + LocalDateTime.now() + "\",\"status\":\"" + HttpServletResponse.SC_FORBIDDEN + "\" , \"message\":\"You are not authorized to access this resource.\"}";

            response.getWriter().write(message);
        };
    }


}
