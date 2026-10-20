package com.spring.boot.restaurant.mappers.security;

import com.spring.boot.restaurant.controllers.vm.Security.AccountAuthRequestVm;
import com.spring.boot.restaurant.controllers.vm.Security.SignUpRequestVm;
import com.spring.boot.restaurant.controllers.vm.Security.AccountAuthResponseVm;
import com.spring.boot.restaurant.dto.security.AccountDto;
import com.spring.boot.restaurant.models.security.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper ACCOUNT_MAPPER =
            Mappers.getMapper(AccountMapper.class);

    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);

    AccountDto toAccountDto(
            AccountAuthRequestVm accountAuthRequestVm
    );

    AccountDto toAccountDto(
            SignUpRequestVm signUpRequestVm
    );

    AccountAuthResponseVm toAccountResponseVm(
            AccountDto accountDto
    );
}