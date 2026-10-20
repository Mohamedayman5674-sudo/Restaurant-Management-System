package com.spring.boot.restaurant.dto.security;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountDetailsDto {

    private Long id;

    @NotEmpty(message = "not_empty.phone_number")
    private String phoneNumber;

    @NotNull(message = "not_empty.age")
    @Min(value = 16, message = "error.age")
    @Max(value = 80, message = "error.age")
    private Integer age;

    @NotEmpty(message = "not_empty.address")
    private String address;
}