package com.test.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

// Publisher confirm is to make sure the publisher send message is in queue and persistent in disk
// successfully persistent RabbitMQ only will send back confirm
// Publisher confirm mode
// 1. Single confirm
// 2. Multiple confirm
// 3. asynchronous multiple confirm
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestConfirmCallback2 implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 1. if message sent to exchange and route failed, it will through publisher return to tell the cause and then return ACK
    // 2. Non-durable message sent to exchange and in queue, return ACK
    // 3. Durable message send to exchange and done persistent in disk, return ACK
    // other reason return NACK
    @Test
    public void testConfirmCallback() throws InterruptedException {

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

    // Depends on which situation, received ack or nack, we need to retry based our logic
    // Example event sent into the queue and persist and return ack, but other queue cannot consume the event, then is the other queue problem

    // For use RabbitMQ library, need to manually add the ack and nack callback to channel
    // channel.addConfirmListener(ConfirmCallback ack, ConfirmCallback nack);
    // channel.basicPublish(Exchange exchange, RoutingKey key ..., Boolean x, AMQP.BasicProperties prop, byte[] body)
    // below is use Spring AMQP override the RabbitTemplate confirm method

    // whenever the message is reach or not reach to exchange or not, confirm will be callback
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("接收到了RabbitMQ的回调id : " + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败 : "+ cause);
        }
    }


}
