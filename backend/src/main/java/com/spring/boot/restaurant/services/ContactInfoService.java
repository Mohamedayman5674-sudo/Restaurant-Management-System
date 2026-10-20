package com.spring.boot.restaurant.services;

import com.spring.boot.restaurant.dto.ContactInfoDto;
import com.spring.boot.restaurant.controllers.vm.ContactInfoResponseVm;

public interface ContactInfoService {

    ContactInfoDto createContactInfo(ContactInfoDto contactInfoDto);

    ContactInfoResponseVm getAllContactInfo(int page, int size);

    void deleteContactInfo(Long id);
}