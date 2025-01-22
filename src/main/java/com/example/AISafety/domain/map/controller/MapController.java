package com.example.AISafety.domain.map.controller;

import com.example.AISafety.domain.map.dto.MapDTO;
import com.example.AISafety.domain.map.service.MapService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class MapController {
    private final MapService mapService;
    @GetMapping("/cctv")
    @PreAuthorize("hasAuthority('ROLE_ROAD_USER') or hasAuthority('ROLE_SAFETY_USER')")
    public List<MapDTO> getCctvLoc(){
        return mapService.getCctvData();
    }

}
