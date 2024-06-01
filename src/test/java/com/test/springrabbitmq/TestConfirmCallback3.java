package com.test.springrabbitmq;

import jakarta.annotation.PostConstruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

// Publisher confirm is to make sure the publisher send message to the exchange
// Publisher confirm mode
// 1. Single confirm
// 2. Multiple confirm
// 3. asynchronous multiple confirm
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestConfirmCallback3 {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){

        // whenever the message is reach or not reach to exchange or not, confirm will be callback
        // when we use anonymous class and lambda method way to set callback, the class no need to implement the RabbitTemplate.ConfirmCallback
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {

                System.out.println("接收到了RabbitMQ的回调id : " + correlationData.getId());
            if (ack) {
                System.out.println("消息成功发送到exchange");
            } else {
                System.out.println("消息失败发送到exchange : "+ cause);
            }

            }
        );

    }

    // don't use this class to test publisher confirm
    @Test
    public void testConfirmCallback() throws InterruptedException {

        String exchangeName = "hmall.direct123"; //only got nack when not able delivered to an exchange
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
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        System.out.println("接收到了RabbitMQ的回调id:{}" + correlationData);
//        if (ack) {
//            System.out.println("消息成功消费");
//        } else {
//            System.out.println("消息消费失败: {}"+ cause);
//        }
//    }
}
