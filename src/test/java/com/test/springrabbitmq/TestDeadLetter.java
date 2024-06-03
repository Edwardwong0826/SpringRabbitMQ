package com.test.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestDeadLetter {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    // There will be 3 scenario message will become dead letter
    // 1. rejected - when message being reject , basicNack() / basicReject() and requeue = false
    // 2. overflow - when queue max length has reached, example 10 max, according to First In First Out, the earlier message will become Dead Letter Message
    // 3. timeout - when message timeout and haven't consume

    // we need to create Dead Letter Exchange (DLX) and queue for receive Dead Letter Message
    // we set the dead letter related config in the normal queue creation arguments
    // x-dead-letter-exchange - The Dead Letter Exchange, DLX
    // x-dead-letter-routing-key - routing key route to which Dead Letter Queue
    // max-length - to set queue maximum length to received message

    // test for scenario 1
    @Test
    public void testDeadLetterMessage(){


        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                "exchange.normal.video",
                "routing.key.normal.video",
                "Test normal",
                correlationData
        );


    }

    // test for scenario 2 and 3
    // when test this stop the RabbitMQ consumer first
    // we will notice the normal queue will only receive 10 message only, because the normal queue max-length is 10 so overflow
    // the other 10 message will directly go to dead letter queue and after like 5-10 seconds
    // all normal queue message will become dead letter message because of timeout has reach
    // and then we can start the RabbitMQ to consume or do whatever on the dead letter message
    @Test
    public void testDeadLetterMessage2(){

        for(int i = 0; i < 20; i++){

            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

            rabbitTemplate.convertAndSend(
                    "exchange.normal.video",
                    "routing.key.normal.video",
                    "Test normal",
                    correlationData
            );
        }
    }

}
