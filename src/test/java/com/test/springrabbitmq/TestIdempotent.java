package com.test.springrabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springrabbitmq.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@SpringBootTest(classes = SpringRabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class TestIdempotent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // this object can do serialize and deserialize for JSON
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testIdempotent() throws JsonProcessingException {

        Orders order1 = Orders.builder().id("order_123456").orderName("buy phone").amount(new BigDecimal("2356")).orderTime(new Date()).build();
        Orders order2 = Orders.builder().id("order_123456").orderName("buy phone").amount(new BigDecimal("2356")).orderTime(new Date()).build();

        // serialize to json
        String jsonOrder1 = objectMapper.writeValueAsString(order1);
        String jsonOrder2 = objectMapper.writeValueAsString(order2);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

        Message message1 = MessageBuilder.withBody(jsonOrder1.getBytes()).andProperties(messageProperties).build();
        Message message2 = MessageBuilder.withBody(jsonOrder2.getBytes()).andProperties(messageProperties).build();

        rabbitTemplate.convertAndSend("exchange.idempotent", "routing.key.idempotent", message1);
        rabbitTemplate.convertAndSend("exchange.idempotent", "routing.key.idempotent", message2);

    }

}
