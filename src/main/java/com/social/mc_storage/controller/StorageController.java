package com.social.mc_storage.controller;

import com.social.mc_storage.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

    private final S3Service service;

    @Autowired
    public StorageController(S3Service service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam String key, @RequestParam String filePath) {
        service.uploadFile(key, filePath);
        return ResponseEntity.ok("File upload successfully!");
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam String key, @RequestParam String downloadPath) {
        service.downloadFile(key, downloadPath);
        return ResponseEntity.ok("File downloaded successfully");
    }
}