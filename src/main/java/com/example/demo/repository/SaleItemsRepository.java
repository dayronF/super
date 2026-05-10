package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.SaleItemsEntity;

@Repository
public interface SaleItemsRepository extends JpaRepository<SaleItemsEntity, Long> {
    List<SaleItemsEntity> findBySaleId(Long saleId);

}
    