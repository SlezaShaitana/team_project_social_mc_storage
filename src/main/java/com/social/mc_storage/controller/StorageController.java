package com.social.mc_storage.controller;

import com.social.mc_storage.dto.StorageDto;
import com.social.mc_storage.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final S3Service service;

    @PostMapping
    public StorageDto uploadToStore(@RequestParam(required = false) MultipartFile file){
        return service.storage(file);
    }
}