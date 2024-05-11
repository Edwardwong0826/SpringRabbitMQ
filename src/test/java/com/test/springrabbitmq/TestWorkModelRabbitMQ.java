package com.test.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestWorkModelRabbitMQ
{
    // inject rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

//    // topic, dynamic route, subscription model
//    @Test
//    public void testTopic()
//    {
//        rabbitTemplate.convertAndSend("topics", "user.save", "user.save route message");
//    }
//
//    // routing
//    @Test
//    public void testRoute()
//    {
//        rabbitTemplate.convertAndSend("logs_direct", "info", "send message to info key");
//    }
//
//    // fanout, Publish/Subscribe
//    @Test
//    public void testFanout()
//    {
//        rabbitTemplate.convertAndSend("logs", "", "Fanout model send message");
//    }
//

    // hello world
    @Test
    public void testHello()
    {
        rabbitTemplate.convertAndSend("simple.queue", "hello world");
    }

    // work queue model
    // the consumer will consume the message 1 times only like round-robin
    //
    @Test
    public void testWorker() throws InterruptedException {
        for(int i = 0; i < 50; i++)
        {
            String queueName = "work.queue";
            String message = "work model " + i;
            rabbitTemplate.convertAndSend(queueName,message);
            Thread.sleep(20);
        }
    }

}
