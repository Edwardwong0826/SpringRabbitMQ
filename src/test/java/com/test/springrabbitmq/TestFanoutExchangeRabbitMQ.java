package com.test.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestFanoutExchangeRabbitMQ {

    // inject rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendFanout() throws InterruptedException {

            String exchangeName = "hmall.fanout";
            String message = "Hello everyone! ";

            // if we don't set routing key, can just set empty or null
            rabbitTemplate.convertAndSend(exchangeName, "",message);

    }

}
