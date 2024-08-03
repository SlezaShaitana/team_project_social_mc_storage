package com.social.mc_storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.social.mc_storage.configuration"})
public class McStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(McStorageApplication.class, args);
	}
}
