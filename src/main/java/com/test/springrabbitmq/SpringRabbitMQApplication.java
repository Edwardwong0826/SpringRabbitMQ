package com.test.springrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRabbitMQApplication {
	// this application is act as the Rabbit MQ Publisher
	// run this spring boot application, go to test folder RabbitMQ each class and type to send the message only can see
	public static void main(String[] args) {
		SpringApplication.run(SpringRabbitMQApplication.class, args);
	}

}
