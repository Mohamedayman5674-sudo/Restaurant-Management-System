package com.spring.boot.restaurant.services.impl;

import com.spring.boot.restaurant.controllers.vm.ContactInfoResponseVm;
import com.spring.boot.restaurant.dto.ContactInfoDto;
import com.spring.boot.restaurant.mappers.ContactInfoMapper;
import com.spring.boot.restaurant.models.ContactInfo;
import com.spring.boot.restaurant.repositories.ContactInfoRepo;
import com.spring.boot.restaurant.services.ContactInfoService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    @Autowired
    private ContactInfoRepo contactInfoRepo;

    @Override
    @CacheEvict(value = "contactInfo", allEntries = true)
    public ContactInfoDto createContactInfo(ContactInfoDto contactInfoDto) {

        try {

            if (Objects.nonNull(contactInfoDto.getId())) {
                throw new SystemException("id.must_be.null");
            }

            ContactInfo contactInfo =
                    ContactInfoMapper.CONTACT_INFO_MAPPER
                            .toContactInfo(contactInfoDto);

            contactInfo = contactInfoRepo.save(contactInfo);

            return ContactInfoMapper.CONTACT_INFO_MAPPER
                    .toContactInfoDto(contactInfo);

        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "contactInfo", key = "'all-' + #page + '-' + #size")
    public ContactInfoResponseVm getAllContactInfo(int page, int size) {

        try {

            if (page < 1) {
                throw new SystemException("error.min.one.page");
            }

            if (size < 1) {
                throw new SystemException("error.min.one.page");
            }

            Pageable pageable = PageRequest.of(page - 1, size);

            Page<ContactInfo> contactInfoPage =
                    contactInfoRepo.findAll(pageable);

            if (contactInfoPage.isEmpty()) {
                throw new SystemException("contact_info.not.found");
            }

            return new ContactInfoResponseVm(
                    contactInfoPage.getContent()
                            .stream()
                            .map(ContactInfoMapper.CONTACT_INFO_MAPPER::toContactInfoDto)
                            .toList(),
                    contactInfoPage.getTotalElements()
            );

        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "contactInfo", allEntries = true)
    public void deleteContactInfo(Long id) {

        try {

            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }

            if (!contactInfoRepo.existsById(id)) {
                throw new SystemException("contact_info.not.found");
            }

            contactInfoRepo.deleteById(id);

        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}