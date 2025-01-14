package com.example.AISafety.domain.animal.service;

import com.example.AISafety.domain.animal.Animal;
import com.example.AISafety.domain.animal.dto.AnimalDTO;
import com.example.AISafety.domain.animal.dto.AnimalResponseDTO;
import com.example.AISafety.domain.animal.repository.AnimalRepository;
import com.example.AISafety.domain.followup.FollowUp;
import com.example.AISafety.domain.followup.Status;
import com.example.AISafety.domain.followup.service.FollowUpService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final FollowUpService followUpService;

    public void save(List<AnimalDTO> animalDTOList){
        for(AnimalDTO animalDTO : animalDTOList){
            Animal animal = new Animal();
            animal.setName(animalDTO.getName());
            animal.setLongitude(animalDTO.getLongitude());
            animal.setLatitude(animalDTO.getLatitude());
            animal.setDetectedAt(LocalDateTime.now());

            animalRepository.save(animal);
        }
    }

    public List<AnimalResponseDTO> animalResponseDTOList(){
        List<Long> animalIds = followUpService.findByStatus(Status.PENDING).stream()
                .map(followUp -> followUp.getAnimal().getId())
                .toList();

        List<Animal>animals = animalRepository.findAll();

        return animals.stream()
                .filter(animal -> !animalIds.contains(animal.getId()))
                .map(animal ->new AnimalResponseDTO(
                        animal.getId(),
                        animal.getName(),
                        animal.getLatitude(),
                        animal.getLongitude(),
                        animal.getDetectedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<AnimalResponseDTO> animalResponseDTOListByOrganization(Long id){
        List<FollowUp> followUps = followUpService.findByOrganizationIdAndStatus(id, Status.PENDING);

        // followUps 에서 연관된 Animal 만 추출 한 것을 animalResponseDTO로 반환
        List<AnimalResponseDTO> animalResponseDTOList = followUps.stream()
                .map(followUp -> followUp.getAnimal())
                .map(animal -> new AnimalResponseDTO(
                        animal.getId(),
                        animal.getName(),
                        animal.getLatitude(),
                        animal.getLongitude(),
                        animal.getDetectedAt()
                ))
                .collect(Collectors.toList());

        return animalResponseDTOList;

    }

    public Map<String,String> deleteAnimal(Long id){
        Map<String, String> response =new HashMap<>();
        if(!animalRepository.existsById(id)){
            response.put("error", "해당 animalID의 동물이 존재하지 않습니다.");
            return response;
        }

        animalRepository.deleteById(id);
        response.put("success", "해당 id 값의 동물이 삭제되었습니다.");
        return response;
    }

    public Animal getAnimal(Long id){
        return animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id 의 동물이 존재하지 않습니다."));
    }

    public Map<String, String> selfHandle(Long id){
        Animal animal = getAnimal(id);
        return followUpService.selfHandle(animal);
    }

    public Map<String, String> otherHandle(Long animalId, Long organizationId){
        Animal animal = getAnimal(animalId);
        return followUpService.otherHandle(animal, organizationId);
    }
}
