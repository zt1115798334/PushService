package com.zt.pushservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.zt.pushservice")
@EntityScan(basePackages = "com.zt.pushservice.mysql.entity")
@EnableJpaRepositories(basePackages = "com.zt.pushservice.mysql.repository")
@EnableAutoConfiguration
@EnableScheduling
public class PushServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PushServiceApplication.class, args);
	}
}
