package com.spring.boot.restaurant.controllers;

import com.spring.boot.restaurant.controllers.vm.ContactInfoResponseVm;
import com.spring.boot.restaurant.dto.ContactInfoDto;
import com.spring.boot.restaurant.services.ContactInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact-info")
public class ContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @PostMapping
    public ResponseEntity<ContactInfoDto> createContactInfo(
            @Valid @RequestBody ContactInfoDto contactInfoDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactInfoService.createContactInfo(contactInfoDto));
    }

    @GetMapping("/all-contact-info")
    public ResponseEntity<ContactInfoResponseVm> getAllContactInfo(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                contactInfoService.getAllContactInfo(page, size)
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteContactInfo(
            @RequestParam Long id) {

        contactInfoService.deleteContactInfo(id);

        return ResponseEntity.noContent().build();
    }
}