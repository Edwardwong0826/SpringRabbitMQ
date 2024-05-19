package com.test.springrabbitmq;

import lombok.extern.slf4j.XSlf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestConfirmCallback2 implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testConfirmCallback() throws InterruptedException {

        String exchangeName = "hmall.direct";
        // inside no args constructor will create UUID for us
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                exchangeName,//交换机
                "red2",//路由键 , this routing key does not exist, is to test ack / nack
                "hello",
                correlationData
        );

        Thread.sleep(2000);
    }

    // depends on which situation, received ack or nack, we need to retry based our logic
    // example event sent into the queue and persist and return ack, but other queue cannot consume the event, then is the other queue problem
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("接收到了RabbitMQ的回调id:{}" + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败: {}"+ cause);
        }
    }


}
