package com.test.springrabbitmq.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutCustomer
{
    @RabbitListener(bindings = {
        @QueueBinding(
                value= @Queue,
                exchange = @Exchange(value = "logs",type = "fanout")
        ) // @Queue didn't specify value, so is random name
    })
    public void receive1(String message)
    {
        System.out.println("message1 = " + message);
    }

    @RabbitListener(bindings = {
        @QueueBinding(
                value= @Queue,
                exchange = @Exchange(value = "logs",type = "fanout")
        ) // @Queue didn't specify value, so is random name
    })
    public void receive2(String message)
    {
        System.out.println("message2 = " + message);
    }
}
