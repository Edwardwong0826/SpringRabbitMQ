package com.test.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestConfirmCallback {

    // inject rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testConfirmCallback() throws InterruptedException {

        String exchangeName = "hmall.direct";
        // inside no args constructor will create UUID for us
        CorrelationData cd = new CorrelationData();

        // After java 8, getFuture no more add callback method, so there is no onSuccess and onFailure,so need to research how do this
        cd.getFuture().whenComplete((result,exception)->{
            System.out.println("Received confirm callback");
            if(result.isAck()){
                System.out.println("Message send success, code is ack");
            } else {
                System.out.println("Message send fail, code is nack, reason :{}" +  result.getReason());
            }

        });

        rabbitTemplate.convertAndSend(exchangeName,"red2", "Hello", cd);

        Thread.sleep(2000);
    }

}
