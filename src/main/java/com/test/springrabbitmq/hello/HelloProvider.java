package com.test.springrabbitmq.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HelloProvider
{
    // inject rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // hello world

    public void send()
    {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }
    
}
