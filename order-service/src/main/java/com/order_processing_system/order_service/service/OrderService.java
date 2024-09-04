package com.order_processing_system.order_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.order_processing_system.order_service.model.Order;
import com.order_processing_system.order_service.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId){
        return orderRepository.findByUserId(userId);
    }
}
