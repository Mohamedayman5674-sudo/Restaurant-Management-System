package com.spring.boot.restaurant.controllers;

import com.spring.boot.restaurant.controllers.vm.AccountResponseVm;
import com.spring.boot.restaurant.dto.security.AccountDto;
import com.spring.boot.restaurant.services.security.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    // =========================
    // GET ALL USERS
    // =========================

    @GetMapping("/all-accounts")
    public ResponseEntity<AccountResponseVm> getAllAccounts(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                accountService.getAccounts(page, size)
        );
    }


    // =========================
    // GET USER BY ID
    // =========================

    @GetMapping("/account-by-id")
    public ResponseEntity<AccountDto> getAccountById(
            @RequestParam Long id) {

        return ResponseEntity.ok(
                accountService.getAccountById(id)
        );
    }


    // =========================
    // CREATE USER
    // =========================

    @PostMapping("/create-account")
    public ResponseEntity<AccountDto> createAccount(
            @RequestBody AccountDto accountDto) {

        return ResponseEntity.ok(
                accountService.createAccount(accountDto)
        );
    }


    // =========================
    // UPDATE USER
    // =========================

    @PutMapping("/update-account")
    public ResponseEntity<AccountDto> updateAccount(
            @RequestBody AccountDto accountDto) {

        return ResponseEntity.ok(
                accountService.updateAccount(accountDto)
        );
    }


    // =========================
    // DELETE USER
    // =========================

    @DeleteMapping("/delete-account")
    public ResponseEntity<Void> deleteAccount(
            @RequestParam Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }
}