package edu.spring.reactive.integration.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.UUID;

@Component
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    @Value("${integration.rabbitmq.exchange}")
    private String exchange;
    @Value("${integration.rabbitmq.routingkey}")
    private String routingKey;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @Retryable(value = {AmqpException.class},
            maxAttemptsExpression = "#{${server.retry.policy.max.attempts:3}}",
            backoff = @Backoff(
                    delayExpression = "#{${server.retry.policy.delay:36000}}",
                    multiplierExpression = "#{${server.retry.policy.multiplier:2}}",
                    maxDelayExpression = "#{${server.retry.policy.max.delay:252000}}"))
    public void onNext() throws IOException {
        String id = UUID.randomUUID().toString();
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                .setCorrelationId(id)
                .setContentEncoding("UTF-8")
                .setHeader("x-delay", 10000)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();

        Message msg = MessageBuilder.withBody(convertToByte("Hola " + id))
                .andProperties(messageProperties).build();

        System.out.printf("[x] Message Pub %s %s \n", id, new Date());
        rabbitTemplate.send(exchange, routingKey, msg);

    }

    private byte[] convertToByte(String obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        return bos.toByteArray();
    }
}
