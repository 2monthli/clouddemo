package org.app.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClientoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientoneApplication.class, args);
	}
}