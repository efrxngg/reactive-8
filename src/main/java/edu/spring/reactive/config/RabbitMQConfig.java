package edu.spring.reactive.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtualhost}")
    private String virtualhostName;

    @Value("${integration.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${integration.rabbitmq.queue}")
    private String queueName;

    @Value("${integration.rabbitmq.routingKey}")
    private String routingKeyName;

    @Bean
    public ConnectionFactory connectionFactory() {
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhostName);
        return connectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory customRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory());
        admin.setIgnoreDeclarationExceptions(true);
        return admin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange exchange() {
        DirectExchange nExchange = new DirectExchange(this.exchangeName);
        nExchange.setAdminsThatShouldDeclare(amqpAdmin());
        nExchange.setDelayed(true);
        nExchange.setIgnoreDeclarationExceptions(true);
        return nExchange;
    }

    @Bean
    public Queue queue() {
        Queue queue = new Queue(this.queueName, true);
        queue.setAdminsThatShouldDeclare(amqpAdmin());
        queue.setIgnoreDeclarationExceptions(true);
        return queue;
    }

    @Bean
    public Binding binding() {
        Binding binding = BindingBuilder.bind(queue()).to(exchange()).with(this.routingKeyName);
        binding.setAdminsThatShouldDeclare(amqpAdmin());
        binding.setIgnoreDeclarationExceptions(true);
        return binding;
    }

}
