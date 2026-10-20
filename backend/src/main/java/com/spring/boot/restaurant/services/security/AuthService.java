package com.spring.boot.restaurant.services.security;

import com.spring.boot.restaurant.controllers.vm.Security.AccountAuthRequestVm;
import com.spring.boot.restaurant.controllers.vm.Security.AccountAuthResponseVm;
import com.spring.boot.restaurant.controllers.vm.Security.SignUpRequestVm;

public interface AuthService {

    AccountAuthResponseVm signUp(
            SignUpRequestVm signUpRequestVm
    );

    AccountAuthResponseVm login(
            AccountAuthRequestVm accountAuthRequestVm
    );
}