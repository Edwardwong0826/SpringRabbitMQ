package com.test.springrabbitmq.config;

import lombok.Data;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RabbitTemplateTransactionalConfig {

 //When want to test transactional only uncomment, else other testing will block by having error throw by Spring
 // CachingConnectionFactory       : Could not configure the channel to receive publisher confirms
//    @Bean
//    public RabbitTransactionManager transactionManager(CachingConnectionFactory connectionFactory){
//        return new RabbitTransactionManager(connectionFactory);
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setChannelTransacted(true);
//        return rabbitTemplate;
//
//    }
}
