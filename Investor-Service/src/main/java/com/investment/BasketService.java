package com.investment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investment.Basket;
import com.investment.BasketRepository;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    public List<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    public List<Basket> getBasketsByAdvisor(String advisorName) {
        return basketRepository.findByCreatedBy(advisorName);
    }

    public Basket getBasketById(Long id) {
        return basketRepository.findById(id).orElseThrow();
    }
}
