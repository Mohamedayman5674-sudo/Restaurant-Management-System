package com.spring.boot.restaurant.controllers.vm.Security;

import com.spring.boot.restaurant.dto.security.AccountDetailsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignUpRequestVm {

    @NotEmpty(message = "not_empty.username")
    @Size(min = 7, message = "size.username")
    private String username;

    @NotEmpty(message = "not_empty.password")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$",
            message = "error.password"
    )
    private String password;

    @NotNull(message = "not_empty.account_details")
    @Valid
    private AccountDetailsDto accountDetails;
}