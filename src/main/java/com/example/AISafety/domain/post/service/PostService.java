package com.example.AISafety.domain.post.service;

import com.example.AISafety.domain.animal.Animal;
import com.example.AISafety.domain.animal.service.AnimalService;
import com.example.AISafety.domain.followup.FollowUp;
import com.example.AISafety.domain.followup.Status;
import com.example.AISafety.domain.followup.service.FollowUpService;
import com.example.AISafety.domain.organization.Organization;
import com.example.AISafety.domain.post.Post;
import com.example.AISafety.domain.post.dto.InfoResponseDTO;
import com.example.AISafety.domain.post.dto.PostRequestDTO;
import com.example.AISafety.domain.post.dto.PostResponseDTO;
import com.example.AISafety.domain.post.repository.PostRepository;
import com.example.AISafety.domain.user.User;
import com.example.AISafety.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FollowUpService followUpService;
    private final UserService userService;
    private final AnimalService animalService;
    private final FileUploadService fileUploadService;

    @Transactional
    public void createPost(PostRequestDTO postRequestDTO, Long userId, MultipartFile file)
            throws IOException {
        Long animalId = postRequestDTO.getAnimalId();
        User user = userService.getUserById(userId);

        //followup 갱신
        FollowUp followUp = followUpService.updateFollowUp(user,animalId);

        String fileUrl = null;
        if (file != null && !file.isEmpty()) {
            // 파일 업로드 시 게시글 ID를 전달
            fileUrl = fileUploadService.uploadFile(file, animalId);  // animalId
        }

        Post post = new Post();
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setFollowup(followUp);
        post.setUser(user);
        post.setFileUrl(fileUrl);

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
                        post.getFollowup().getId(),
                        post.getFileUrl()
                ))
                .toList();
    }

    public List<PostResponseDTO>getPostsByOrganization(Long userId){

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
                        post.getFollowup().getId(), // FollowUp의 ID
                        post.getFileUrl()
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
               post.getFollowup().getId(),
               post.getFileUrl()
       );
    }

    public InfoResponseDTO getInfo(Long userId, Long animalId){
        User user = userService.getUserById(userId);
        Organization organization = user.getOrganization();
        Animal animal = animalService.getAnimal(animalId);
        return new InfoResponseDTO(
                animal.getDetectedAt(),
                animal.getLatitude(),
                animal.getLongitude(),
                organization.getName(),
                animal.getName(),
                user.getName()
        );
    }

}
