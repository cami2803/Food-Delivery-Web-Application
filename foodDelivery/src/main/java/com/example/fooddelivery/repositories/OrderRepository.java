package com.example.fooddelivery.repositories;

import com.example.fooddelivery.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID>{
}
