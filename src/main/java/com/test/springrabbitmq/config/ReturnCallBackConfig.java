package com.test.springrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReturnCallBackConfig implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("Received message return callback, exchange:{}, key:{}, msg:{}, code:{},text:{} ", returnedMessage.getExchange(),
                returnedMessage.getRoutingKey(), returnedMessage.getMessage(),
                returnedMessage.getReplyCode(), returnedMessage.getReplyText());
    }
}
