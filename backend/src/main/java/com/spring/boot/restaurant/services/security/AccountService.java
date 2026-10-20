package com.spring.boot.restaurant.services.security;

import com.spring.boot.restaurant.controllers.vm.AccountResponseVm;
import com.spring.boot.restaurant.dto.security.AccountDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAccounts();

    AccountResponseVm getAccounts(int page, int size);

    AccountDto createAccount(AccountDto accountDto);

    AccountDto updateAccount(AccountDto accountDto);

    void deleteAccount(Long id);

    AccountDto getAccountById(Long id);

    AccountDto getAccountByUsername(String username);
}