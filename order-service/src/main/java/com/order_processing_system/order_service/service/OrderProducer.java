package com.order_processing_system.order_service.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.order_processing_system.order_service.model.Order;
import com.order_processing_system.order_service.repository.OrderRepository;

@Service
public class OrderProducer {

    private final AmqpTemplate amqpTemplate;
    private final OrderRepository orderRepository;

    @Value("${order.exchange.name}")
    private String exchange;

    @Value("${order.routing.key}")
    private String routingKey;

    public OrderProducer(AmqpTemplate amqpTemplate, OrderRepository orderRepository) {
        this.amqpTemplate = amqpTemplate;
        this.orderRepository = orderRepository;
    }

    public void sendOrderCreatedMessage(Order order){
        Order savedOrder = orderRepository.save(order);
        amqpTemplate.convertAndSend(exchange, routingKey, savedOrder);
        System.out.println("Order created and sent to queue: " + savedOrder);
    }
}
