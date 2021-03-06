package com.mentalcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"com"})
@EnableJpaRepositories(basePackages={"com.dao","com.repository"})
@EntityScan(basePackages={"com.model"})
public class MentalcareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentalcareApplication.class, args);
	}

}
