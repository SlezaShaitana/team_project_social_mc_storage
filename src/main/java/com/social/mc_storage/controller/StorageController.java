package com.social.mc_storage.controller;

import com.social.mc_storage.dto.StorageDto;
import com.social.mc_storage.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
@Slf4j
public class StorageController {

    private final S3Service service;

    @PostMapping
    public StorageDto uploadToStore(@RequestParam(name = "file", required = false) MultipartFile file){
        log.info("Controller file {}", file);
        return service.storage(file);
    }
}