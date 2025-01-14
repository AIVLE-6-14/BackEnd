package com.example.AISafety.domain.organization.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class OrganizationDTO {
    private String name;
    private String number;
    private String address;
}
