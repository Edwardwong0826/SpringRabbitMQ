package com.test.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestPersistent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testPageOut(){

        Message message = MessageBuilder.withBody("hello".getBytes(StandardCharsets.UTF_8)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        // Spring AMQP by default send message is durable
        // purposely send message without durable many times to test page out scenario
        // page out is when memory is full or publisher send message more than consumer capability or consumer down / slow processing will cause message stack and block
        // when page out happen, the entire queues is block and cannot be process to wait persist to disk storage to free some memory area
        // RabbitMQ after 3.8.26 version, the exchange, queue is by default create with durable
        // for the data to be persistent, exchange, queue and send message all need to be durable

        // in application.yaml need to disable publisher confirm and return when test page out, else will very slow when test this page out
        //  publisher-confirm-type: correlated
        //  publisher-returns: true

        for(int i = 0; i < 1000000; i++){
            rabbitTemplate.convertAndSend("simple.queue", message);
        }

    }
}
