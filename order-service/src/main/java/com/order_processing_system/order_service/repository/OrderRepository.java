package com.order_processing_system.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.order_processing_system.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
