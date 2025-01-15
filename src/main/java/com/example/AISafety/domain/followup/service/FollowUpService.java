package com.example.AISafety.domain.followup.service;

import com.example.AISafety.domain.animal.Animal;
import com.example.AISafety.domain.followup.FollowUp;
import com.example.AISafety.domain.followup.Status;
import com.example.AISafety.domain.followup.repository.FollowUpRepository;
import com.example.AISafety.domain.organization.service.OrganizationService;
import com.example.AISafety.domain.user.User;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowUpService {
    private final FollowUpRepository followUpRepository;
    private final OrganizationService organizationService;

    public Map<String,String> selfHandle(Animal animal){

        FollowUp follow = new FollowUp();
        follow.setAnimal(animal);
        follow.setOrganization(organizationService.getOrganizationByID(1L));
        follow.setStatus(Status.PENDING);
        follow.setCreatedAt(LocalDateTime.now());

        followUpRepository.save(follow);
        Map<String, String> response = new HashMap<>();
        response.put("success", "follow 저장 성공!");
        return response;
    }

    public Map<String, String> otherHandle(Animal animal, Long organizationId){
        FollowUp follow = new FollowUp();
        follow.setAnimal(animal);
        follow.setOrganization(organizationService.getOrganizationByID(organizationId));
        follow.setStatus(Status.PENDING);
        follow.setCreatedAt(LocalDateTime.now());

        followUpRepository.save(follow);
        Map<String, String> response = new HashMap<>();
        response.put("success", "follow 저장 성공!");
        return response;
    }

    public List<FollowUp> findByOrganizationIdAndStatus(Long id, Status status) {
        return followUpRepository.findByOrganizationIdAndStatus(id, status);
    }

    public List<FollowUp> findByStatus(Status status){
        return followUpRepository.findByStatus(status);
    }

    public FollowUp findByAnimalId(long  animalId){
        return followUpRepository.findByAnimalId(animalId);
    }

    public FollowUp updateFollowUp(User user, Long animalId){
        FollowUp follow = findByAnimalId(animalId);
        follow.setStatus(Status.COMPLETED);
        follow.setUser(user);
        follow.setUpdatedAt(LocalDateTime.now());

        followUpRepository.save(follow);

        return follow;
    }
}
