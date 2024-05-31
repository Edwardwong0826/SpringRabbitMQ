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


@Slf4j
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestReturnCallback3 {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    // when we use anonymous class and lambda method way to set the callback, the class no need to implement the RabbitTemplate.ReturnsCallback methods
    // return only get callback when exchange message is not able to reach to the queue
    // after run the @test method, check the return message from spring boot stack log not the test method stack log
    @PostConstruct
    public void init(){
        rabbitTemplate.setReturnsCallback( returnedMessage -> {
            log.error("Received message return callback, exchange:{}, key:{}, msg:{}, code:{},text:{} ", returnedMessage.getExchange(),
                    returnedMessage.getRoutingKey(), returnedMessage.getMessage(),
                    returnedMessage.getReplyCode(), returnedMessage.getReplyText());
           }
        );
    }

    @Test
    public void testReturnCallback3() throws InterruptedException {

        String exchangeName = "hmall.direct";
        // inside no args constructor will create UUID for us
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                exchangeName,//交换机
                "red123123",//路由键 , this routing key does not exist, is to test ack / nack, so the return callback message should have NO_ROUTE
                "hello",
                correlationData
        );

    }

}
