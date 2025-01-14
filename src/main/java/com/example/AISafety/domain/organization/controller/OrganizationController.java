package com.example.AISafety.domain.organization.controller;

import com.example.AISafety.domain.organization.dto.OrganizationDTO;
import com.example.AISafety.domain.organization.service.OrganizationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveOrganization(@RequestBody List<OrganizationDTO> organizationDTOList){
        organizationService.saveOrganizations(organizationDTOList);
        Map<String,String> response = new HashMap<>();
        response.put("message", "organizations 저장 성공 !");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
