package com.example.AISafety.domain.post.controller;

import com.example.AISafety.domain.post.dto.PostRequestDTO;
import com.example.AISafety.domain.post.dto.PostResponseDTO;
import com.example.AISafety.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name="Post API", description = "게시물 관련 API 제공합니다.")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    @Operation(summary="게시물 생성 기능", description = "동물id, 제목, 내용, 유저 세션을 받아서 게시물을 생성합니다.")
    public ResponseEntity<Map<String,String>> createPost(@RequestBody PostRequestDTO requestDTO, HttpSession session) {
        Map<String, String> response = new HashMap<>();

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            response.put("FAIL", "로그인 인증 실패");
            response.put("message", "로그인을 해주세요.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        try {
            postService.createPost(requestDTO, session);
            response.put("SUCCESS", "게시글 등록 성공");
            response.put("message", "새로운 게시글 등록에 성공하셨습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            response.put("FAIL", "예기치 못한 에러 발생");
            response.put("message", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

        // 모든 포스트 조회
        @GetMapping("/all")
        @Operation(summary="게시물 전체 조회 기능", description = "모든 게시물을 보여줍니다.")
        public ResponseEntity<Map<String, Object>> getAllPosts(){
            List<PostResponseDTO> allPosts = postService.getAllPosts();
            Map<String,Object> response = new HashMap<>();
            response.put("SUCCESS", "전체 게시물 조회 성공");
            response.put("message", allPosts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // 특정 기관의 포스트 조회
        @GetMapping("/organization")
        @Operation(summary="게시물 기관 조회 기능", description = "해당 유저의 세션을 받아 해당 유저가 속한 기관 게시물을 보여줍니다.")
        public ResponseEntity<Map<String,Object>> getPostsByOrganization(HttpSession session){
            List<PostResponseDTO> postsByOrganization = postService.getPostsByOrganization(session);
            Map<String,Object> response = new HashMap<>();
            response.put("SUCCESS", "특정 기관의 게시물 조회 성공");
            response.put("message", postsByOrganization);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // 게시물 상세 조회
        @GetMapping("/detail/{id}")
        public ResponseEntity<Map<String, Object>> getPostById(@PathVariable("id") Long id){
            PostResponseDTO postById = postService.getPostById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("SUCCESS", "게시물 상세 조회 성공");
            response.put("message", postById);

            return new ResponseEntity<>(response,HttpStatus.OK);
        }


}

