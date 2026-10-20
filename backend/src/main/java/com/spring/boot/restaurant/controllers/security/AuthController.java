package com.spring.boot.restaurant.controllers.security;

import com.spring.boot.restaurant.controllers.vm.Security.AccountAuthRequestVm;
import com.spring.boot.restaurant.controllers.vm.Security.AccountAuthResponseVm;
import com.spring.boot.restaurant.controllers.vm.Security.SignUpRequestVm;
import com.spring.boot.restaurant.services.security.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(
        name = "Auth Controller",
        description = "Sign up, login"
)
@RequestMapping("/auth")
@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/sign-up")
    public ResponseEntity<AccountAuthResponseVm> signUp(
            @RequestBody @Valid SignUpRequestVm signUpRequestVm
    ) throws SystemException {

        return ResponseEntity
                .created(URI.create("/sign-up"))
                .body(
                        authService.signUp(signUpRequestVm)
                );
    }


    @PostMapping("/login")
    public ResponseEntity<AccountAuthResponseVm> login(
            @RequestBody @Valid AccountAuthRequestVm accountAuthRequestVm
    ) throws SystemException {

        return ResponseEntity
                .ok(
                        authService.login(accountAuthRequestVm)
                );
    }
}