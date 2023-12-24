package com.joaonini75.auctionpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuctionpiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuctionpiApplication.class, args);
	}
}
