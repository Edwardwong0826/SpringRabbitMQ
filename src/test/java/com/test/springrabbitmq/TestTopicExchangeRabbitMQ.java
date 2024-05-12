package com.test.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestTopicExchangeRabbitMQ {

    // inject rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendTopic1() throws InterruptedException {

        String exchangeName = "hmall.topic";
        String message = "China weather";

        // if we don't set routing key, can just set empty or null
        // direct exchange will send the message to bind queue according the routing key
        rabbitTemplate.convertAndSend(exchangeName, "china.weather", message);

    }

    @Test
    public void testSendTopic2() throws InterruptedException {

        String exchangeName = "hmall.topic";
        String message = "Japan news";

        // if we don't set routing key, can just set empty or null
        // direct exchange will send the message to bind queue according the routing key
        rabbitTemplate.convertAndSend(exchangeName, "japan.news", message);

    }

}
