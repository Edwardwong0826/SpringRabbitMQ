package com.test.springrabbitmq.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkCustomer
{
    // here parameter type is the one producer message type, not always will be string
    // work queue model is round rabin schedule when have multiple message come in
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void received1(String message)
    {
        System.out.println("message1 = " + message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void received2(String message)
    {
        System.out.println("message2 = " + message);
    }
}
