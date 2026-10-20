package com.spring.boot.restaurant.dto;

import com.spring.boot.restaurant.models.BundleMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    private BundleMessage bundleMessage;
}
