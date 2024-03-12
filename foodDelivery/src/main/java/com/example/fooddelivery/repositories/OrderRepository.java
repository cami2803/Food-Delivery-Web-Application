package com.example.fooddelivery.repositories;

import com.example.fooddelivery.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>{
}
