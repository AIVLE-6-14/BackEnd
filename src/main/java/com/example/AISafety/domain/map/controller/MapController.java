package com.example.AISafety.domain.map.controller;

import com.example.AISafety.domain.map.dto.MapDTO;
import com.example.AISafety.domain.map.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Animal API", description = "CCTV관련 API 를 제공합니다.")
@RequestMapping("/api/map")
public class MapController {
    private final MapService mapService;
    @GetMapping("/cctv")
    @PreAuthorize("hasAuthority('ROLE_ROAD_USER') or hasAuthority('ROLE_SAFETY_USER')")
    @Operation(summary="전국 cctv 데이터 조회", description = "리스트 형태로 CCTV 위치, 이름, 영상 정보를 조회 할 수 있습니다.")
    public List<MapDTO> getCctvLoc(){
        return mapService.getCctvData();
    }

}
