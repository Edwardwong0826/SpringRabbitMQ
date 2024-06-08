package com.test.springrabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestTransactional {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String EXCHANGE_NAME = "exchange.tx";

    public static final String ROUTING_NAME = "routing.key.tx";

    // In RabbitMQ, the transactional only effective at publisher side and has limit on it, cannot achieve distributed transactional
    // RabbitMQ when send message using RabbitTemplate.convertAndSend() it, the message will first save in cache and then send to broker
    // submit transaction means the message in cache send to broker, if we don't submit and rollback transaction then not send to broker
    // because cannot guarantee message from cache will surely send to broker so transactional in rabbitMQ actually very limit to cache only
    // primary purpose is to ensure every operation in java successfully executed and submit transaction
    @Test
    @Transactional
    @Rollback(value = false) // junit default will roll back transaction, want to test submit transaction set it to false
    public void testSendMessageInTx(){
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_NAME, "TXN message - 01");

        // be in mind currently testing got issue because CachingConnectionFactory Could not configure the channel to receive publisher confirms, need to further look at this
        // since is not very important, if really want to test this transactional how it works on rabbitMQ
        // if got exception it will trigger rollback transaction, so then both message will send to queue
        //log.info("do bad:" + 10 / 0);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_NAME, "TXN message - 02");
    }

}
