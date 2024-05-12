package com.test.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestDirectExchangeRouteRabbitMQ {

    // inject rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendDirect() throws InterruptedException {

        String exchangeName = "hmall.direct";
        String message = "Hello Red ";
        String message2 = "Hello Blue";
        String message3 = "Hello Yellow";

        // if we don't set routing key, can just set empty or null
        // direct exchange will send the message to bind queue according the routing key
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
        rabbitTemplate.convertAndSend(exchangeName, "blue", message2);
        rabbitTemplate.convertAndSend(exchangeName, "yellow", message3);
    }

}
