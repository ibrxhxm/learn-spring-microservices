package com.example.photoappaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoappAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoappAccountApplication.class, args);
	}

}
