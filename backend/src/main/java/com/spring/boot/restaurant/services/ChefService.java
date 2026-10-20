package com.spring.boot.restaurant.services;

import com.spring.boot.restaurant.controllers.vm.ChefResponseVm;
import com.spring.boot.restaurant.dto.ChefDto;
import jakarta.transaction.SystemException;

public interface ChefService {

    ChefResponseVm getAllChefs(int page, int size) throws SystemException;

    ChefDto addChef(ChefDto chefDto) throws SystemException;

    ChefDto updateChef(ChefDto chefDto) throws SystemException;

    void deleteChef(Long id) throws SystemException;
}