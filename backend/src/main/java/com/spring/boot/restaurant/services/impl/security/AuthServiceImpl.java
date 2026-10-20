package com.spring.boot.restaurant.services.impl.security;

import com.spring.boot.restaurant.config.security.TokenHandler;
import com.spring.boot.restaurant.controllers.vm.Security.AccountAuthRequestVm;
import com.spring.boot.restaurant.controllers.vm.Security.AccountAuthResponseVm;
import com.spring.boot.restaurant.controllers.vm.Security.SignUpRequestVm;
import com.spring.boot.restaurant.dto.security.AccountDto;
import com.spring.boot.restaurant.mappers.security.AccountMapper;
import com.spring.boot.restaurant.services.security.AccountService;
import com.spring.boot.restaurant.services.security.AuthService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AccountAuthResponseVm signUp(
            SignUpRequestVm signUpRequestVm
    ) {

        AccountDto accountDto =
                AccountMapper.ACCOUNT_MAPPER
                        .toAccountDto(signUpRequestVm);

        accountDto =
                accountService.createAccount(accountDto);

        AccountAuthResponseVm response =
                AccountMapper.ACCOUNT_MAPPER
                        .toAccountResponseVm(accountDto);

        response.setToken(
                tokenHandler.generateToken(accountDto)
        );

        response.setUserRoles(
                getAccountRoles(accountDto)
        );

        return response;
    }


    @Override
    public AccountAuthResponseVm login(
            AccountAuthRequestVm accountAuthRequestVm
    ) {

        try {

            AccountDto accountDto =
                    accountService.getAccountByUsername(
                            accountAuthRequestVm.getUsername()
                    );

            if (Objects.isNull(accountDto)) {
                throw new SystemException(
                        "not_found.account"
                );
            }

            if (!passwordEncoder.matches(
                    accountAuthRequestVm.getPassword(),
                    accountDto.getPassword()
            )) {

                throw new SystemException(
                        "error.invalid.credentials"
                );
            }

            AccountAuthResponseVm response =
                    AccountMapper.ACCOUNT_MAPPER
                            .toAccountResponseVm(accountDto);

            response.setToken(
                    tokenHandler.generateToken(accountDto)
            );

            response.setUserRoles(
                    getAccountRoles(accountDto)
            );

            return response;

        } catch (SystemException e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    private List<String> getAccountRoles(
            AccountDto accountDto
    ) {

        return accountDto.getRoles()
                .stream()
                .map(roleDto ->
                        roleDto.getCode().name()
                )
                .collect(Collectors.toList());
    }
}