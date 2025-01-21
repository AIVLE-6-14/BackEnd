package com.example.AISafety.domain.organization.service;

import com.example.AISafety.domain.organization.Organization;
import com.example.AISafety.domain.organization.dto.OrganizationDTO;
import com.example.AISafety.domain.organization.dto.ResponseDTO;
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
            organization.setLatitude(organizationDTO.getLatitude());
            organization.setLongitude(organizationDTO.getLongitude());
            organizationRepository.save(organization);
        }
    }

    public Organization getOrganizationByID(Long id){
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id 를 가진 organization 은 존재하지 않습니다."));
    }

    public List<ResponseDTO>getOrganizationsName(){
        return organizationRepository.findAll().stream()
                .map(organization -> new ResponseDTO(
                        organization.getName()
                ))
                .toList();
    }

    public Long findNearOrganizationId(double latitude, double longitude){
        List<Organization> organizationRepositoryAll = organizationRepository.findAll();
        Organization near = null;
        double shortestDistance = Double.MAX_VALUE;

        for (Organization organization : organizationRepositoryAll) {
            if(organization.getId() == 1) continue;
            double distance = calculateDistance(latitude, longitude, organization.getLatitude(), organization.getLongitude());
            if (distance < shortestDistance) {
                shortestDistance = distance;
                near = organization;
            }
        }

        if (near != null) {
            return near.getId();
        } else {
            throw new EntityNotFoundException("가장 가까운 조직을 찾을 수 없습니다.");
        }

    }


    // 두 지점 간의 거리 계산 (하버사인 공식)
    private double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        final int R = 6371; // 지구 반지름 (단위: km)

        double lat1 = Math.toRadians(latitude1);
        double lon1 = Math.toRadians(longitude1);
        double lat2 = Math.toRadians(latitude2);
        double lon2 = Math.toRadians(longitude2);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 결과는 킬로미터 단위
    }

}
