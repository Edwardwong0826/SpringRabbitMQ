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

// use this class to test publisher confirm and publisher return
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQReliability {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // to test able send message to exchange and successfully route to queue
    @Test
    public void testConfirmCallback() throws InterruptedException {

        String exchangeName = "hmall.direct"; //only got nack when not able delivered to an exchange
        // inside no args constructor will create UUID for us
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                exchangeName,//交换机
                "red",//路由键 , this routing key does not exist
                "hello",
                correlationData
        );

        //Thread.sleep(2000);
    }

    // to test unable send message to exchange
    @Test
    public void testConfirmCallback2() throws InterruptedException {

        String exchangeName = "hmall.direct123"; //only got nack when not able delivered to an exchange
        // inside no args constructor will create UUID for us
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                exchangeName,//交换机
                "red",//路由键 , this routing key does not exist
                "hello",
                correlationData
        );

        //Thread.sleep(2000);
    }

    // to test message sent to exchange but unable route to queue
    // here returnedMessage will get callback
    @Test
    public void testConfirmCallback3() throws InterruptedException {

        String exchangeName = "hmall.direct";
        // inside no args constructor will create UUID for us
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                exchangeName,//交换机
                "red2",//路由键 , this routing key does not exist
                "hello",
                correlationData
        );

        //Thread.sleep(2000);
    }

}
