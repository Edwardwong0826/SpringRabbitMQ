package com.test.springrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MQConfirmConfig implements ApplicationContextAware {

    // this is one of the way implement publisher return for RabbitMQ, there is several ways to implement
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // set call back
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.error("Received message return callback, exchange:{}, key:{}, msg:{}, code:{},text:{} ", returnedMessage.getExchange(),
                        returnedMessage.getRoutingKey(), returnedMessage.getMessage(),
                        returnedMessage.getReplyCode(), returnedMessage.getReplyText());
            }
        });
    }
}
