package com.example.AISafety.domain.animal.controller;

import com.example.AISafety.domain.animal.dto.AnimalDTO;
import com.example.AISafety.domain.animal.dto.AnimalResponseDTO;
import com.example.AISafety.domain.animal.service.AnimalService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalService animalService;
    @PostMapping("/fetch")
    public ResponseEntity<Map<String, String>> saveAnimal(@RequestBody List<AnimalDTO> animalDTOList){
        animalService.save(animalDTOList);
        Map<String,String> response = new HashMap<>();
        response.put("message", "성공적으로 동물감지 저장!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 도로교통공사 모든 알림
    @GetMapping
    public ResponseEntity<List<AnimalResponseDTO>> getAllAnimals(){
        List<AnimalResponseDTO> animalResponseDTOS = animalService.animalResponseDTOList();
        return ResponseEntity.ok(animalResponseDTOS);
    }

    //야생동물 보호 기관 해당하는 알림
    @GetMapping("/{organizationId}")
    public ResponseEntity<List<AnimalResponseDTO>> getAnimalsByOrganizationId(
            @PathVariable("organizationId") Long organizationId){
        List<AnimalResponseDTO> animalResponseDTOS = animalService.animalResponseDTOListByOrganization(organizationId);
        return  ResponseEntity.ok(animalResponseDTOS);

    }

    //추후에 구현 방식을 바꿔야 할듯.. ?
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAnimal(@PathVariable("id") Long id){
        Map<String,String> response = animalService.deleteAnimal(id);

        return ResponseEntity.ok(response);
    }

    //animal 자체 처리 요총 컨트롤러
    @PostMapping("/{id}/self-handle")
    public ResponseEntity<Map<String, String>> handleSelf(@PathVariable("id") Long id){
        Map<String, String> response = animalService.selfHandle(id);
        return ResponseEntity.ok(response);
    }

    //animal 야생동물보호 처리 요청 컨트롤러
    @PostMapping("/{animalId}/{organizationId}/other-handle")
    public ResponseEntity<Map<String, String>> handleOther(
            @PathVariable("animalId") Long animalId,
            @PathVariable("organizationId") Long organizationId){
        Map<String, String> response = animalService.otherHandle(animalId,organizationId);
        return ResponseEntity.ok(response);
    }


}
