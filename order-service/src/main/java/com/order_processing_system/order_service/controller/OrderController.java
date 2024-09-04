package com.order_processing_system.order_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.order_processing_system.order_service.model.Order;
import com.order_processing_system.order_service.model.OrderItem;
import com.order_processing_system.order_service.service.OrderProducer;
import com.order_processing_system.order_service.service.OrderService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderProducer orderProducer;
    private final OrderService orderService;

    public OrderController(OrderProducer orderProducer, OrderService orderService) {
        this.orderProducer = orderProducer;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId){
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
        }

        orderProducer.sendOrderCreatedMessage(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order placed with ID: " + order.getOrderId());
    }

}
