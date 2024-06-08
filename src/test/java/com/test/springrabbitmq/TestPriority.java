package com.test.springrabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@Slf4j
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestPriority {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public static final String EXCHANGE_PRIORITY = "exchange.test.priority";

    public static final String ROUTING_KEY_PRIORITY = "routing.key.test.priority";

    @Test
    public void testSendMessagePriority(){

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(EXCHANGE_PRIORITY, ROUTING_KEY_PRIORITY, "message test priority 1", message -> {

            // Set the message priority value
            // the priority value cannot more than x-max-priority value
            // in this case queue.test.priority x-max-priority : 10
            // message priority value that larger will be prioritized first and broker will send that message first
            message.getMessageProperties().setPriority(1);

            return message;
        }, correlationData);

    }

    @Test
    public void testSendMessagePriority2(){

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(EXCHANGE_PRIORITY, ROUTING_KEY_PRIORITY, "message test priority 2", message -> {

            // Set the message priority value
            // the priority value cannot more than x-max-priority value
            // in this case queue.test.priority x-max-priority : 10
            // message priority value that larger will be prioritized first and broker will send that message first
            message.getMessageProperties().setPriority(2);

            return message;
        }, correlationData);

    }

    @Test
    public void testSendMessagePriority3(){

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(EXCHANGE_PRIORITY, ROUTING_KEY_PRIORITY, "message test priority 3", message -> {

            // Set the message priority value
            // the priority value cannot more than x-max-priority value
            // in this case queue.test.priority x-max-priority : 10
            // message priority value that larger will be prioritized first and broker will send that message first
            message.getMessageProperties().setPriority(3);

            return message;
        }, correlationData);

    }
}
