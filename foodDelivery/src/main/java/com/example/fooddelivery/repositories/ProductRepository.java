package com.example.fooddelivery.repositories;

import com.example.fooddelivery.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
