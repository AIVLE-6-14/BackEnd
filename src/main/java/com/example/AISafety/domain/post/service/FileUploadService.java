package com.example.AISafety.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final S3Client s3Client; // S3Client 사용

    @Value("${aws.s3.bucket}") // 버킷 이름 주입
    private String bucketName;

    public String uploadFile(MultipartFile file, Long postId) throws IOException {
        if (file == null || file.isEmpty()) {
            System.out.println("The file is empty or null.");
            return null;
        }

        // 파일명을 postId 기반으로 변경 (예: uploads/{postId}_image.jpg)
        String fileName = "uploads/" + postId + "_" + file.getOriginalFilename();
        System.out.println("Uploading file to S3: " + fileName);

        // 파일 스트림 얻기
        InputStream fileInputStream = file.getInputStream();

        // PutObjectRequest 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        // S3에 파일 업로드
        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileInputStream, file.getSize()));

        // 업로드된 파일 URL 반환
        String fileUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toString();
        System.out.println("Uploaded file URL: " + fileUrl);
        return fileUrl;
    }
}
