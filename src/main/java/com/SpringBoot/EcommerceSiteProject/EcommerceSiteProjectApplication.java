package com.SpringBoot.EcommerceSiteProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude ={SecurityAutoConfiguration.class})


public class EcommerceSiteProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceSiteProjectApplication.class, args);
	}

}
