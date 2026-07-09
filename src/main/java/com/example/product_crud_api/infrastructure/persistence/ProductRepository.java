package com.example.product_crud_api.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_crud_api.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
