package com.example.photoappdiscservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PhotoappDiscServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoappDiscServiceApplication.class, args);
	}

}
