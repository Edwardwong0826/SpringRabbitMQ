package com.test.springrabbitmq;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringRabbitMQApplication {

	// this application is act as the Rabbit MQ Publisher
	// run this spring boot application, go to test folder RabbitMQ each class and type to send the message only can see
	public static void main(String[] args) {
		SpringApplication.run(SpringRabbitMQApplication.class, args);
	}

	@Bean
	public MessageConverter jacksonMessageConvertor(){

		Jackson2JsonMessageConverter jjmc = new Jackson2JsonMessageConverter();
		jjmc.setCreateMessageIds(true);
		return jjmc;

	}

}
