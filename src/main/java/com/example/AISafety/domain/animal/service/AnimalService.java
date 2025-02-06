package com.example.AISafety.domain.animal.service;

import com.example.AISafety.domain.animal.Animal;
import com.example.AISafety.domain.animal.dto.AnimalDTO;
import com.example.AISafety.domain.animal.dto.AnimalResponseDTO;
import com.example.AISafety.domain.animal.dto.OtherHandleDTO;
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
            animal.setImgUrl(animalDTO.getImgUrl());
            animalRepository.save(animal);
        }
    }

    public List<AnimalResponseDTO> allAnimalResponseDTOList(){
        List<Animal> all = animalRepository.findAll();

       return all.stream()
               .map(animal -> new AnimalResponseDTO(
                       animal.getId(),
                       animal.getName(),
                       animal.getLatitude(),
                       animal.getLongitude(),
                       animal.getImgUrl(),
                       animal.getDetectedAt()
               ))
               .toList();

    }

    public List<AnimalResponseDTO> animalResponseDTOList(){
        // PENDING 상태의 FollowUp에서 관련된 동물 ID 추출
        List<Long> animalIds = followUpService.findByStatus(Status.PENDING).stream()
                .map(followUp -> followUp.getAnimal().getId())
                .toList();

        // COMPLETED 상태의 FollowUp에서 관련된 동물 ID 추출
        List<Long> completedAnimalIds = followUpService.findByStatus(Status.COMPLETED).stream()
                .map(followUp -> followUp.getAnimal().getId())
                .toList();

        // 모든 동물 목록 가져오기
        List<Animal> animals = animalRepository.findAll();

        return animals.stream()
                .filter(animal -> !animalIds.contains(animal.getId()) && !completedAnimalIds.contains(animal.getId()))  // PENDING과 COMPLETED 제외
                .map(animal -> new AnimalResponseDTO(
                        animal.getId(),
                        animal.getName(),
                        animal.getLatitude(),
                        animal.getLongitude(),
                        animal.getImgUrl(),
                        animal.getDetectedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<AnimalResponseDTO> animalResponseDTOListByOrganization(Long id){
        // 해당 기관의 PENDING 상태 FollowUp 목록
        List<FollowUp> followUps = followUpService.findByOrganizationIdAndStatus(id, Status.PENDING);

        // PENDING 상태의 동물 ID 추출
        List<Long> animalIds = followUps.stream()
                .map(followUp -> followUp.getAnimal().getId())
                .toList();

        // 기관에 연관된 동물들만 추출하여 DTO로 변환

        return followUps.stream()
                .map(followUp -> followUp.getAnimal())
                .filter(animal -> animalIds.contains(animal.getId()))  // PENDING 인것만
                .map(animal -> new AnimalResponseDTO(
                        animal.getId(),
                        animal.getName(),
                        animal.getLatitude(),
                        animal.getLongitude(),
                        animal.getImgUrl(),
                        animal.getDetectedAt()
                ))
                .collect(Collectors.toList());

    }

    public Map<String,String> deleteAnimal(Long id){
        Map<String, String> response =new HashMap<>();
        if(!animalRepository.existsById(id)){
            response.put("FAIL","삭제 실패");
            response.put("message", "해당 animalID의 동물이 존재하지 않습니다.");
            return response;
        }

        animalRepository.deleteById(id);
        response.put("SUCCESS", "삭제 성공");
        response.put("message", "해당 id 값의 동물이 삭제되었습니다.");
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

    public Map<String, String> otherHandle(Long animalId, OtherHandleDTO otherHandleDTO){
        Animal animal = getAnimal(animalId);

        return followUpService.otherHandle(animal, otherHandleDTO);
    }

}
