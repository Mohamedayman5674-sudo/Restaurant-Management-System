package com.spring.boot.restaurant.config.security.filters;

import com.spring.boot.restaurant.config.security.TokenHandler;
import com.spring.boot.restaurant.dto.security.AccountDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.SystemException;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Lazy
    @Autowired
    private TokenHandler tokenHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String token =
                    request.getHeader("Authorization");

            // =========================
            // Check Authorization Header
            // =========================

            if (Objects.isNull(token)
                    || !token.startsWith("Bearer ")) {

                response.reset();
                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );
                return;
            }

            // Remove "Bearer "
            token = token.substring(7);

            // =========================
            // Validate JWT
            // =========================

            AccountDto userValidated =
                    tokenHandler.validateToken(token);

            if (Objects.isNull(userValidated)) {

                response.reset();
                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );
                return;
            }

            // =========================
            // Get User Roles
            // =========================

            List<SimpleGrantedAuthority> roles =
                    userValidated.getRoles()
                            .stream()
                            .map(role ->
                                    new SimpleGrantedAuthority(
                                            "ROLE_"
                                                    + role.getCode().name()
                                    )
                            )
                            .toList();

            // =========================
            // Create Authentication
            // =========================

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userValidated,
                            userValidated.getPassword(),
                            roles
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            // Continue request
            filterChain.doFilter(request, response);

        } catch (SystemException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {

        // =========================
        // CORS Preflight
        // =========================

        if ("OPTIONS".equalsIgnoreCase(
                request.getMethod())) {

            return true;
        }

        String uri =
                request.getRequestURI();

        // =========================
        // Public Auth APIs
        // =========================

        if (uri.equals("/auth/sign-up")) {
            return true;
        }

        if (uri.equals("/auth/login")) {
            return true;
        }

        // =========================
        // Swagger
        // =========================

        if (uri.startsWith("/swagger-ui")) {
            return true;
        }

        if (uri.startsWith("/v3/api-docs")) {
            return true;
        }

        if (uri.startsWith("/swagger-resources")) {
            return true;
        }

        if (uri.startsWith("/webjars")) {
            return true;
        }

        // =========================
        // All other requests
        // require JWT
        // =========================

        return false;
    }
}