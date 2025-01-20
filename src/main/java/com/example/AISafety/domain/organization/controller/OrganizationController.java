package com.example.AISafety.domain.organization.controller;

import com.example.AISafety.domain.organization.Organization;
import com.example.AISafety.domain.organization.dto.OrganizationDTO;
import com.example.AISafety.domain.organization.dto.ResponseDTO;
import com.example.AISafety.domain.organization.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/organizations")
@Tag(name="Organization API", description = "기관 관련 API 제공합니다.")
public class OrganizationController {
    private final OrganizationService organizationService;
    @PostMapping("/save")
    @Operation(summary="기관 등록 기능", description = "리스트 형태로 기관을 등록할 수 있습니다.")
    public ResponseEntity<Map<String, String>> saveOrganization(@RequestBody List<OrganizationDTO> organizationDTOList){
        organizationService.saveOrganizations(organizationDTOList);
        Map<String,String> response = new HashMap<>();
        response.put("SUCCESS", "organizations 저장 성공");
        response.put("message", "organizations 저장에 성공하셨습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary="등록된 기관 이름 조회", description = "등록된 모든 기관들의 이름을 반환해줍니다.")
    public ResponseEntity<Map<String, Object>> oranizationsName(){
        List<ResponseDTO> organizationsName = organizationService.getOrganizationsName();
        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS", "organizations 불러오기 성공");
        response.put("message", organizationsName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{organizationId}")
    @Operation(summary="단건 등록된 기관 이름 조회", description = "해당된 아이디의 기관의 이름을 반환해줍니다.")
    public ResponseEntity<Map<String, Object>> getOrganizationById(@PathVariable("organizationId") Long organizationId) {
        Organization organization = organizationService.getOrganizationByID(organizationId);
        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS", "소속 이름 조회 성공");
        response.put("message", organization.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
