package com.example.AISafety.domain.organization.service;

import com.example.AISafety.domain.organization.Organization;
import com.example.AISafety.domain.organization.dto.OrganizationDTO;
import com.example.AISafety.domain.organization.repository.OrganizationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public void saveOrganizations(List<OrganizationDTO> organizationDTOList){
        for(OrganizationDTO organizationDTO : organizationDTOList){
            Organization organization = new Organization();
            organization.setName(organizationDTO.getName());
            organization.setNumber(organizationDTO.getNumber());
            organization.setAddress(organizationDTO.getAddress());
            organizationRepository.save(organization);
        }
    }

    public Organization getOrganizationByID(Long id){
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id 를 가진 organization 은 존재하지 않습니다."));
    }
}
