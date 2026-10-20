package com.spring.boot.restaurant.controllers.vm;

import com.spring.boot.restaurant.dto.ContactInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactInfoResponseVm {

    private List<ContactInfoDto> contactInfo;

    private Long totalContactInfo;
}