package com.example.AISafety.domain.map.service;

import com.example.AISafety.domain.map.dto.MapDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MapService {

    private final RestTemplate restTemplate;

    @Value("${cctv.api.key}")
    private String apiKey;

    @Value("${cctv.api.url}")
    private String apiUrl;

    @Value("${cctv.api.type}")
    private String type;

    @Value("${cctv.api.cctvType}")
    private String cctvType;

    @Value("${cctv.api.minX}")
    private String minX;

    @Value("${cctv.api.maxX}")
    private String maxX;

    @Value("${cctv.api.minY}")
    private String minY;

    @Value("${cctv.api.maxY}")
    private String maxY;

    @Value("${cctv.api.getType}")
    private String getType;

    public List<MapDTO> getCctvData() {
        // API URL 구성
        String url = String.format(
                "%s?apiKey=%s&type=%s&cctvType=%s&minX=%s&maxX=%s&minY=%s&maxY=%s&getType=%s",
                apiUrl, apiKey, type, cctvType, minX, maxX, minY, maxY, getType
        );

        try {
            // RestTemplate을 사용해 GET 요청을 보냅니다.
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseData = response.getBody();

                // 'response'는 Map으로 반환되며, 그 안에 'data'라는 리스트가 있습니다.
                Map<String, Object> responseBody = (Map<String, Object>) responseData.get("response");

                // 'data'는 List 형식입니다.
                List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get("data");

                // CCTV 정보들을 List<MapDTO> 형태로 변환
                List<MapDTO> result = new ArrayList<>();
                for (Map<String, Object> item : data) {
                    Double coordx = (Double) item.get("coordx");
                    Double coordy = (Double) item.get("coordy");
                    String cctvname = (String) item.get("cctvname");
                    String cctvurl = (String) item.get("cctvurl");

                    // MapDTO 객체 생성 및 추가
                    MapDTO dto = new MapDTO(cctvurl, coordx, coordy, cctvname);
                    result.add(dto);
                }

                return result;
            } else {
                throw new RuntimeException("CCTV 데이터 가져오기 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("CCTV 데이터 가져오기 실패: " + e.getMessage(), e);
        }
    }
}
