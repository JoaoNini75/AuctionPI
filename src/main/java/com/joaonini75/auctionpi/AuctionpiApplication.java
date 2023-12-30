package com.joaonini75.auctionpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class AuctionpiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuctionpiApplication.class, args);
	}
}

@Configuration
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {

}
