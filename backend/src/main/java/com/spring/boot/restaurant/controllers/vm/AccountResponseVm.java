package com.spring.boot.restaurant.controllers.vm;

import com.spring.boot.restaurant.dto.security.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountResponseVm {

    private List<AccountDto> accounts;

    private long totalAccounts;
}