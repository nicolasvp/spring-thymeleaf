package com.spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.test.models.service.IUploadFileService;

@SpringBootApplication
public class SpringTestThymeleafApplication implements CommandLineRunner{

	@Autowired
	IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringTestThymeleafApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}
}
