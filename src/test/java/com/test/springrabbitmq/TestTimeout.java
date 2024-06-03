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
public class TestTimeout {

    public static final String EXCHANGE_NAME = "exchange.test.timeout";
    public static final String ROUTING_KEY = "routing.key.test.timeout";
    public static final String ROUTING_KEY2 = "routing.key.test.timeout2";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // We can set message timeout so that it will auto delete from queue after it reach time to live
    // actually those timeout message will get remove from queue and become dead letter
    @Test
    public void testTimeout(){


        for(int i =0; i < 100; i ++) {
            // we can set the message timeout during the queue creation
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(
                    EXCHANGE_NAME,
                    ROUTING_KEY,
                    "Test timeout" + i,
                    correlationData
            );
        }

    }

    @Test
    public void testTimeout2(){

        // we can set specific message timeout using MessageProperties
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setExpiration("6000");
            return message;
        };

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                EXCHANGE_NAME,//交换机
                ROUTING_KEY2,
                "Test timeout",
                messagePostProcessor,
                correlationData
        );


    }
}
