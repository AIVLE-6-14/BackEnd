package com.example.AISafety.domain.post.controller;

import com.example.AISafety.domain.post.dto.PostRequestDTO;
import com.example.AISafety.domain.post.dto.PostResponseDTO;
import com.example.AISafety.domain.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createPost(@RequestBody PostRequestDTO requestDTO, HttpSession session) {
        Map<String, String> response = new HashMap<>();

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            response.put("error", "로그인을 해주세요");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        try {
            postService.createPost(requestDTO, session);
            response.put("success", "새로운 게시글 성공적으로 등록 완료");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.put("error", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

        // 모든 포스트 조회
        @GetMapping("/all")
        public ResponseEntity<List<PostResponseDTO>> getAllPosts(){
            List<PostResponseDTO> allPosts = postService.getAllPosts();
            return ResponseEntity.ok(allPosts);
        }

        // 특정 기관의 포스트 조회
        @GetMapping("/organization")
        public ResponseEntity<List<PostResponseDTO>> getPostsByOrganization(HttpSession session){
            List<PostResponseDTO> postsByOrganization = postService.getPostsByOrganization(session);
            return ResponseEntity.ok(postsByOrganization);
        }


}

