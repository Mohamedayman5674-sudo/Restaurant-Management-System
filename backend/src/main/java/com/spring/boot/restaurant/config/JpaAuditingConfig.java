package com.spring.boot.restaurant.config;

import com.spring.boot.restaurant.dto.security.AccountDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {

        return () -> {

            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            if (authentication == null
                    || !authentication.isAuthenticated()
                    || authentication.getPrincipal() == null) {

                return Optional.empty();
            }

            Object principal =
                    authentication.getPrincipal();

            if (principal instanceof AccountDto) {

                AccountDto accountDto =
                        (AccountDto) principal;

                return Optional.of(
                        accountDto.getUsername()
                );
            }

            return Optional.empty();
        };
    }
}