package com.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Investor-ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Investor-ServiceApplication.class, args);
	}

}
