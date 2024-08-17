package com.orderserrvice.producer;

import com.orderserrvice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderPublisher {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key.order.name}")
    private String routingKey;

    @Value("${rabbitmq.routing.key.order.email.name}")
    private String emailRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public boolean publicOrderMessage(OrderEvent orderEvent) {
//        boolean isMessagePublished = false;
        log.info("public order event message "+ orderEvent);
        try {
            rabbitTemplate.convertAndSend(exchange,routingKey, orderEvent);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean publishToEmailQueue(OrderEvent orderEvent) {
        log.info("publish to email queue "+ orderEvent);
        try{
            rabbitTemplate.convertAndSend(exchange,emailRoutingKey, orderEvent);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
