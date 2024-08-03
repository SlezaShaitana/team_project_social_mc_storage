package com.social.mc_storage.controller;

import com.social.mc_storage.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final S3Service service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam String fileName, @RequestParam MultipartFile filePath) {
        try {
            service.uploadFile(fileName, filePath);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam String key, @RequestParam String downloadPath) {
        service.downloadFile(key, downloadPath);
        return ResponseEntity.ok("File downloaded successfully");
    }
}