package com.kharim.orderservice.repository;

import com.kharim.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {


}
