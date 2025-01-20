package com.example.AISafety.domain.animal.controller;

import com.example.AISafety.domain.animal.dto.AnimalDTO;
import com.example.AISafety.domain.animal.dto.AnimalResponseDTO;
import com.example.AISafety.domain.animal.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name="Animal API", description = "동물 관련(알림) API 를 제공합니다.")
public class AnimalController {
    private final AnimalService animalService;
    //파이썬 서버 전용 API, 인증 필요 x
    @PostMapping("/fetch")
    @Operation(summary="동물 감지 등록 기능", description = "동물 감지 정보를 받아 저장합니다.")
    public ResponseEntity<Map<String, String>> saveAnimal(@RequestBody List<AnimalDTO> animalDTOList){
        animalService.save(animalDTOList);
        Map<String,String> response = new HashMap<>();
        response.put("SUCCESS", "동물 감지 저장 성공");
        response.put("message", "동물 감지 저장에 성공하셨습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 도로교통공사 모든 알림
    // 도로 교통 공사  role 만 가능하게
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ROAD_USER')")
    @Operation(summary="도로교통 공사 알림 조회 기능", description = "PENDING 처리가 되지 않은 모든 동물 감지 알림을 보여줍니다.")
    public ResponseEntity<Map<String, Object>> getAllAnimals(){
        List<AnimalResponseDTO> animalResponseDTOS = animalService.animalResponseDTOList();
        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS", "알람 조회 성공");
        response.put("message", animalResponseDTOS);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //야생동물 보호 기관 해당하는 알림
    @GetMapping("/{organizationId}")
    @PreAuthorize("hasRole('ROLE_SAFETY_USER')")
    @Operation(summary="야생동물 보호 기관 알림 조회 기능", description = "PENDING 처리가 된 동물 감지 알림을 보여줍니다. pathVariable로 해당 기관의 ID 가 필요합니다.")
    public ResponseEntity<Map<String, Object>> getAnimalsByOrganizationId(
            @PathVariable("organizationId") Long organizationId){
        List<AnimalResponseDTO> animalResponseDTOS = animalService.animalResponseDTOListByOrganization(organizationId);
        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS", "알림 조회 성공");
        response.put("message", animalResponseDTOS);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    //추후에 구현 방식을 바꿔야 할듯.. ?
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ROAD_USER')")
    @Operation(summary="알림 삭제 기능", description = "animal_id를 PathVariable로 받아 삭제합니다. - 오감지 시 사용")
    public ResponseEntity<Map<String, String>> deleteAnimal(@PathVariable("id") Long id){
        Map<String,String> response = animalService.deleteAnimal(id);
        return ResponseEntity.ok(response);
    }

    //animal 자체 처리 요청 컨트롤러
    @PostMapping("/{id}/self-handle")
    @PreAuthorize("hasRole('ROLE_ROAD_USER')")
    @Operation(summary="도로교통 공사 자체 처리 기능", description = "animal_id를 PathVariable로 받아 해당 동물감지에 대한 자체 처리 요청 을 수행합니다.(테이블 생성)")
    public ResponseEntity<Map<String, String>> handleSelf(@PathVariable("id") Long id){
        Map<String, String> response = animalService.selfHandle(id);
        return ResponseEntity.ok(response);
    }

    //animal 야생동물보호 처리 요청 컨트롤러
    @PostMapping("/{animalId}/{organizationId}/other-handle")
    @PreAuthorize("hasRole('ROLE_ROAD_USER')")
    @Operation(summary="야생동물 보호기관 처리 요청 기능", description = "animal_id와 organization_id를 PathVariable로 받아 해당 동물감지에 대한 자체 처리 요청 을 수행합니다.(테이블 생성)")
    public ResponseEntity<Map<String, String>> handleOther(
            @PathVariable("animalId") Long animalId,
            @PathVariable("organizationId") Long organizationId){
        Map<String, String> response = animalService.otherHandle(animalId,organizationId);
        return ResponseEntity.ok(response);
    }


}
