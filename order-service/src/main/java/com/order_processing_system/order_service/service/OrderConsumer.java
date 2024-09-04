package com.order_processing_system.order_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @RabbitListener(queues = "inventory.response.queue")
    public void receiveInventoryResponse(String message) {
        System.out.println("Received Inventory Response: " + message);
        // Process the inventory response
    }

    @RabbitListener(queues = "payment.response.queue")
    public void receivePaymentResponse(String message) {
        System.out.println("Received Payment Response: " + message);
        // Process the payment response
    }
}
