package com.social.mc_storage.service;

import com.social.mc_storage.dto.StorageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.InputStream;
import java.net.URI;


@Service
@Slf4j
public class S3Service {

    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final S3Client s3Client;

    public S3Service(
            @Value("${cloud.yandex.s3.access-key}") String accessKey,
            @Value("${cloud.yandex.s3.secret-key}") String secretKey,
            @Value("${cloud.yandex.s3.bucket-name}") String bucketName) {

        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;

        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create("https://storage.yandexcloud.net"))
                .region(Region.of("ru-central1"))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKey, this.secretKey)))
                .build();
    }

    public StorageDto storage(MultipartFile file) {
        log.info("Uploading file {}", file.getOriginalFilename());
        String fileName = file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        try(InputStream inputStream = file.getInputStream()) {
            long startTime = System.currentTimeMillis();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
            long endTime = System.currentTimeMillis();
            log.info("The image has been successfully sent to the storage in {} ms", (endTime - startTime));
        } catch (Exception ex) {
            log.error("The image was not sent to the storage, an error occurred: {}", ex.getMessage());
            throw new RuntimeException("Failed to upload file to storage", ex);
        }

        StorageDto storageDto = new StorageDto();
        storageDto.setFileName(String.format("https://%s.%s/%s", bucketName, "storage.yandexcloud.net", fileName));
        return storageDto;
    }
}