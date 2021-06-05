package com.test.springrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRabbitMQApplication {
	// run this spring boot application, go to test folder TestRabbitMQ each type to send the message only can see
	public static void main(String[] args) {
		SpringApplication.run(SpringRabbitMQApplication.class, args);
	}

}
