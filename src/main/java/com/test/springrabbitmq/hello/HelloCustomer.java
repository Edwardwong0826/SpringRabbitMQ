package com.test.springrabbitmq.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queuesToDeclare = @Queue(value = "hello", durable = "false", autoDelete ="true" ))
//@RabbitListener(queuesToDeclare = @Queue)
public class HelloCustomer
{
//    @RabbitHandler
//    public void received(String message)
//    {
//        System.out.println("message = " + message);
//    }
}
