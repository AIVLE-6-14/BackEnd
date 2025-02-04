package com.example.AISafety.domain.post.controller;

import static com.example.AISafety.global.security.jwt.JwtUtil.getCurrentUserId;

import com.example.AISafety.domain.post.dto.InfoResponseDTO;
import com.example.AISafety.domain.post.dto.PostRequestDTO;
import com.example.AISafety.domain.post.dto.PostResponseDTO;
import com.example.AISafety.domain.post.service.PostService;
import com.example.AISafety.domain.predict.service.PredictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name="Post API", description = "게시물 관련 API 제공합니다.")
public class PostController {

    private final PostService postService;
    private final PredictService predictService;

    @PostMapping("/create")
    @Operation(summary="게시물 생성 기능", description = "JWT 토큰에서 유저 정보를 가져와 게시물을 생성합니다.")
    public ResponseEntity<Map<String, Object>> createPost(
            @RequestParam(value = "file", required = false) MultipartFile file,  // 파일을 받을 때
            @ModelAttribute PostRequestDTO postRequestDTO
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            Long userId = getCurrentUserId();
            postService.createPost(postRequestDTO,userId, file);
            predictService.savePredict(postRequestDTO.getAnimalId());
            response.put("SUCCESS", "게시글 등록 성공");
            response.put("fileUrl", "post 생성이 성공적으로 진행 되었습니다.");  // 파일 URL이 포함된 응답 반환
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("FAIL", "게시글 등록 실패");
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    // 모든 포스트 조회
        @GetMapping("/all")
        @PreAuthorize("hasRole('ROLE_ROAD_USER')")
        @Operation(summary="게시물 전체 조회 기능", description = "모든 게시물을 보여줍니다.")
        public ResponseEntity<Map<String, Object>> getAllPosts(){
            List<PostResponseDTO> allPosts = postService.getAllPosts();
            Map<String,Object> response = new HashMap<>();
            response.put("SUCCESS", "전체 게시물 조회 성공");
            response.put("message", allPosts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

//         특정 기관의 포스트 조회
        @GetMapping("/organization")
        @PreAuthorize("hasRole('ROLE_SAFETY_USER')")
        @Operation(summary="게시물 기관 조회 기능", description = "해당 유저의 세션을 받아 해당 유저가 속한 기관 게시물을 보여줍니다.")
        public ResponseEntity<Map<String,Object>> getPostsByOrganization(){
            Long id = getCurrentUserId();
            List<PostResponseDTO> postsByOrganization = postService.getPostsByOrganization(id);
            Map<String,Object> response = new HashMap<>();
            response.put("SUCCESS", "특정 기관의 게시물 조회 성공");
            response.put("message", postsByOrganization);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // 게시물 상세 조회
        @GetMapping("/detail/{id}")
        @PreAuthorize("hasAnyRole('ROLE_ROAD_USER', 'ROLE_SAFETY_USER')")
        @Operation(summary="특정 게시물 상세보기", description = "특정 게시물에 대한 상세 정보를 반환합니다.")
        public ResponseEntity<Map<String, Object>> getPostById(@PathVariable("id") Long id){
            PostResponseDTO postById = postService.getPostById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("SUCCESS", "게시물 상세 조회 성공");
            response.put("message", postById);

            return new ResponseEntity<>(response,HttpStatus.OK);
        }

        @GetMapping("/info/{animalId}")
        @PreAuthorize("hasAnyRole('ROLE_ROAD_USER', 'ROLE_SAFETY_USER')")
        public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("animalId") Long animalId){
            Long userId = getCurrentUserId();
            InfoResponseDTO info = postService.getInfo(userId, animalId);
            Map<String,Object> response = new HashMap<>();
            response.put("SUCCESS", "기본 정보 조회 성공");
            response.put("message", info);

            return new ResponseEntity<>(response,HttpStatus.OK);
        }
}

