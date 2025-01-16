package com.example.AISafety.domain.post.service;

import com.example.AISafety.domain.followup.FollowUp;
import com.example.AISafety.domain.followup.Status;
import com.example.AISafety.domain.followup.service.FollowUpService;
import com.example.AISafety.domain.organization.service.OrganizationService;
import com.example.AISafety.domain.post.Post;
import com.example.AISafety.domain.post.dto.PostRequestDTO;
import com.example.AISafety.domain.post.dto.PostResponseDTO;
import com.example.AISafety.domain.post.repository.PostRepository;
import com.example.AISafety.domain.user.User;
import com.example.AISafety.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FollowUpService followUpService;
    private final UserService userService;
    private final OrganizationService organizationService;

    @Transactional
    public void createPost(PostRequestDTO postRequestDTO, HttpSession session){
        Long animalId = postRequestDTO.getAnimalId();
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        //followup 갱신
        FollowUp followUp = followUpService.updateFollowUp(user,animalId);

        Post post = new Post();
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setFollowup(followUp);
        post.setUser(user);

        postRepository.save(post);
    }

    public List<PostResponseDTO> getAllPosts(){
        return postRepository.findAll().stream()
                .map(post -> new PostResponseDTO(
                        post.getId(),
                        post.getFollowup().getOrganization().getName(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        post.getUser().getId(),
                        post.getFollowup().getId()
                ))
                .toList();
    }

    public List<PostResponseDTO>getPostsByOrganization(HttpSession session){
        Long userId = (Long) session.getAttribute("userId");

        User user = userService.getUserById(userId);
        Long organizationId = user.getOrganization().getId();

        List<FollowUp> followUps = followUpService.findByOrganizationIdAndStatus(organizationId,
                Status.COMPLETED);

        // FollowUp에서 연관된 Post를 찾기
        List<PostResponseDTO> postResponseDTOs = followUps.stream()
                .flatMap(followUp -> followUp.getPosts().stream()) // FollowUp에서 Post들의 리스트를 flatMap으로 평평하게 처리
                .map(post -> new PostResponseDTO(
                        post.getId(),
                        post.getFollowup().getOrganization().getName(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        post.getUser().getId(), // User의 ID
                        post.getFollowup().getId() // FollowUp의 ID
                ))
                .toList();

        return postResponseDTOs;

    }

    public PostResponseDTO getPostById(Long id){
       Post post = postRepository.findById(id)
               .orElseThrow(()->new RuntimeException("해당 ID의 게시물이 존재하지 않습니다."));

       return new PostResponseDTO(
               post.getId(),
               post.getFollowup().getOrganization().getName(),
               post.getTitle(),
               post.getContent(),
               post.getCreatedAt(),
               post.getUser().getId(),
               post.getFollowup().getId()
       );
    }
}
