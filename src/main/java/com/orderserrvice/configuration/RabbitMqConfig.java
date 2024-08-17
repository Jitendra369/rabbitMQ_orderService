package com.orderserrvice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queue.name.order}")
    private String orderQueue;

    @Value("${rabbitmq.exchange.name}")
    private String topicExchange;

    @Value("${rabbitmq.routing.key.order.name}")
    private String orderRoutingQueue;

    @Value("${rabbitmq.queue.name.email}")
    private String emailQueue;

    @Value("${rabbitmq.routing.key.order.email.name}")
    private String emailRoutingKey;

    @Bean
    public Queue ordersQueue() {
        return new Queue(orderQueue);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder
                .bind(ordersQueue())
                .to(orderExchange())
                .with(orderRoutingQueue);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(orderExchange())
                .with(emailRoutingKey);
    }

//    message converter > json to java && java to json
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

//    configure rabbitMq template
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
