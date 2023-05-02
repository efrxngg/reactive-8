package edu.spring.reactive.integration.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Date;

@Component
public class EventListener {

    private final EventPublisher pub;

    public EventListener(EventPublisher pub) {
        this.pub = pub;
    }

    @RabbitListener(
            queues = "${integration.rabbitmq.queue}",
            containerFactory = "customRabbitListenerContainerFactory"
    )
    @Async
    public void onMessage(Message message) throws IOException, ClassNotFoundException {
        String correlationId = message.getMessageProperties().getCorrelationId();
        System.out.printf("[x] Message Sub %s %s \n", correlationId, new Date());
        System.out.println("[x] content: " + convert(message.getBody()));
    }

    private String convert(byte[] body) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(body);
        ObjectInput in = new ObjectInputStream(bis);
        return (String) in.readObject();
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void sendToRabbit() throws IOException {
        pub.onNext();
    }

}
