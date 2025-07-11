package com.investment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByCreatedBy(String createdBy);
}
