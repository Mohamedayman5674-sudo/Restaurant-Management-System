package com.spring.boot.restaurant.config.security;

import com.spring.boot.restaurant.config.security.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors ->
                cors.configurationSource(corsConfigurationSource())
        );

        http.authorizeHttpRequests(auth -> auth

                // =========================
                // LOGIN & SIGN UP
                // =========================

                .requestMatchers(
                        "/auth/sign-up",
                        "/auth/login"
                ).permitAll()


                // =========================
                // SWAGGER
                // =========================

                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()


                // =========================
                // ANGULAR ADMIN PAGES
                // =========================

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")


                // =========================
                // ACCOUNT MANAGEMENT
                // ADMIN ONLY
                // =========================

                .requestMatchers("/accounts/**")
                .hasRole("ADMIN")


                // =========================
                // PRODUCT MANAGEMENT
                // ADMIN ONLY
                // =========================

                .requestMatchers(
                        HttpMethod.POST,
                        "/products/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.PUT,
                        "/products/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/products/**"
                ).hasRole("ADMIN")


                // =========================
                // CATEGORY MANAGEMENT
                // ADMIN ONLY
                // =========================

                .requestMatchers(
                        HttpMethod.POST,
                        "/categories/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.PUT,
                        "/categories/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/categories/**"
                ).hasRole("ADMIN")


                // =========================
                // CHEF MANAGEMENT
                // ADMIN ONLY
                // =========================

                .requestMatchers(
                        HttpMethod.POST,
                        "/chefs/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.PUT,
                        "/chefs/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/chefs/**"
                ).hasRole("ADMIN")


                // =========================
                // PRODUCT DETAILS
                // ADMIN ONLY
                // =========================

                .requestMatchers("/product-details/**")
                .hasRole("ADMIN")


                // =========================
                // CONTACT INFORMATION
                // =========================

                // User can send contact message
                .requestMatchers(
                        HttpMethod.POST,
                        "/contact-info"
                ).authenticated()

                // Admin manages contact messages
                .requestMatchers(
                        HttpMethod.GET,
                        "/contact-info/all-contact-info"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/contact-info/delete"
                ).hasRole("ADMIN")


                // =========================
                // ORDERS
                // =========================

                // Logged-in user can create an order
                .requestMatchers(
                        HttpMethod.POST,
                        "/order/create-order"
                ).authenticated()

                // Logged-in user can see his orders
                .requestMatchers(
                        HttpMethod.GET,
                        "/order/my-orders"
                ).authenticated()

                // Admin can see all orders
                .requestMatchers(
                        HttpMethod.GET,
                        "/order/all-orders"
                ).hasRole("ADMIN")

                // Admin can search orders
                .requestMatchers(
                        HttpMethod.GET,
                        "/order/search"
                ).hasRole("ADMIN")


                // =========================
                // EVERYTHING ELSE
                // LOGIN REQUIRED
                // =========================

                .anyRequest()
                .authenticated()
        );


        // =========================
        // JWT FILTER
        // =========================

        http.addFilterBefore(
                authFilter,
                UsernamePasswordAuthenticationFilter.class
        );


        return http.build();
    }


    // =========================
    // CORS
    // =========================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:4200")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }


    // =========================
    // PASSWORD ENCODER
    // =========================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}