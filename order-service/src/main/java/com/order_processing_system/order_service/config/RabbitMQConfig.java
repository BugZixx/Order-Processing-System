package com.order_processing_system.order_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue orderQueue(){
        return new Queue("order.queue", true);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order.exchange");
    }

    @Bean
    public Queue inventoryResponseQueue(){
        return new Queue("inventory.response.queue", false);
    }

    @Bean
    public Queue paymentResponseQueue(){
        return new Queue("payment.response.queue", false);
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.created");
    }

    @Bean
    public Binding inventoryResponseBinding(Queue inventoryResponseQueue, TopicExchange orderExchange){
        return BindingBuilder.bind(inventoryResponseQueue).to(orderExchange).with("inventory.checked");
    }

    @Bean
    public Binding paymentResponseBinding(Queue paymentResponseQueue, TopicExchange orderExchange){
        return BindingBuilder.bind(paymentResponseQueue).to(orderExchange).with("payment.processed");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
