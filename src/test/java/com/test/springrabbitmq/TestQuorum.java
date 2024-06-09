package com.test.springrabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@Slf4j
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestQuorum {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String EXCHANGE_NAME = "exchange.quorum.test";

    public static final String ROUTING_NAME = "routing.key.quorum.test";

    // Quorum queue is the modern queue type that is recommended by the RabbitMQ official
    // https://www.rabbitmq.com/docs/quorum-queues
    // https://raft.github.io/ - Raft Consensus Algorithm
    // it was default durable and replica FIFO queue based on the Raft consensus algorithm
    // means it can sync between the RabbitMQ cluster node without like classic queue need to enable High Availability and set up mirroring queue using policies
    // and there is some difference between  quorum queue and classic queue, further details refer to RabbitMQ documentation for quorum queue to check
    // once we created the quorum queue we will see in the management console there is +2 on the node tab and the details it got mentioned the leader node and member nodes
    @Test
    public void testSendMessageToQuorum(){


        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(
                EXCHANGE_NAME,
                ROUTING_NAME,
                "Test quorum queue",
                correlationData
        );

    }

}
