package com.test.springrabbitmq;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

// Publisher Return
// is to ensure message is it send to queue
// 1. Message got send to MQ, but route failed due to wrong routing key or no binding queue, publisher return callback send the cause and return ACK
@Slf4j
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestReturnCallback2 implements RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setReturnsCallback(this);
    }

    @Test
    public void testReturnCallback2() throws InterruptedException {

        String exchangeName = "hmall.direct";
        // inside no args constructor will create UUID for us
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                exchangeName,//交换机
                "red123123",//路由键 , this routing key does not exist, is to test ack / nack
                "hello",
                correlationData
        );

    }

    // return only get callback when exchange message is not able to reach to the queue
    // after run the @test method, check the return message from spring boot stack log not the test method stack log
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("Received message return callback, exchange:{}, key:{}, msg:{}, code:{},text:{} ", returnedMessage.getExchange(),
                returnedMessage.getRoutingKey(), returnedMessage.getMessage(),
                returnedMessage.getReplyCode(), returnedMessage.getReplyText());
    }
}
