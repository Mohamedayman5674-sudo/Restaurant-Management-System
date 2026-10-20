package com.spring.boot.restaurant.controllers.vm;

import com.spring.boot.restaurant.dto.ChefDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChefResponseVm {

    private List<ChefDto> chefs;
    private long totalChefs;
}