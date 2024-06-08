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

        // In application.yaml need to disable publisher confirm and return when test page out, else will very slow when test this page out
        // publisher-confirm-type: correlated
        // publisher-returns: true

        // Start from RabbitMQ 3.12, all classic queue when create will be default to lazy queue already and the performance almost the same with non-lazy queue
        // So that message will straight persist to disk and keep small amount messages in memory (up to 2048)
        // More refer to official documentation https://www.rabbitmq.com/docs/lazy-queues

        // Lazy queue is a queue type that it's durability was Durable instead of Transient
        // from the official documentation it mentioned
        // A "lazy queue" is a classic queue which is running in lazy mode. When the "lazy" queue mode is set,
        // messages in classic queues are moved to disk as early as practically possible. These messages are loaded into RAM only when they are requested by consumers.
        // which also means non-lazy queue persistent was wait until it needed to persist only will persist
        for(int i = 0; i < 1000000; i++){
            rabbitTemplate.convertAndSend("simple.queue", message);
        }

    }

}
