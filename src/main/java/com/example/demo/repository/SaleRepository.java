package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SalesEntity;

public interface SaleRepository extends JpaRepository<SalesEntity, Long> {
    List<SalesEntity> findByCashierId(Long cashierId);
}