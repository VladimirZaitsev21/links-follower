package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public ConnectionFactory connectionFactory(
            String rabbitHost,
            int rabbitPort,
            String rabbitVirtualHost,
            String rabbitUser,
            String rabbitPassword
    ) {
        var connectionFactory = new CachingConnectionFactory(rabbitHost, rabbitPort);
        connectionFactory.setUsername(rabbitUser);
        connectionFactory.setPassword(rabbitPassword);
        connectionFactory.setVirtualHost(rabbitVirtualHost);
        return connectionFactory;
    }
    @Bean
    public Queue queue(String rabbitQueue, String rabbitRoutingKey, String rabbitExchange) {
        return QueueBuilder.durable(rabbitQueue)
                .withArgument("x-dead-letter-exchange", rabbitExchange)
                .withArgument("x-dead-letter-routing-key", rabbitRoutingKey + ".dlq")
                .build();
    }

    @Bean
    public Queue deadLetterQueue(String rabbitQueue) {
        return QueueBuilder.durable(rabbitQueue + ".dlq").build();
    }

    @Bean
    public DirectExchange directExchange(String rabbitExchange) {
        return new DirectExchange(rabbitExchange, true, false);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange, String rabbitRoutingKey) {
        return BindingBuilder.bind(queue).to(directExchange).with(rabbitRoutingKey);
    }

    @Bean
    public Binding deadBinding(Queue deadLetterQueue, DirectExchange directExchange, String rabbitRoutingKey) {
        return BindingBuilder.bind(deadLetterQueue).to(directExchange).with(rabbitRoutingKey + ".dlq");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter("*");
    }

}
