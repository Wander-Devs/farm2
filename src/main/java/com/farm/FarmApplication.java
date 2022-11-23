package com.farm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.farm"}, exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories("com.farm.repo")
@EntityScan("com.farm.model")
public class FarmApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(FarmApplication.class, args);
	}

}
