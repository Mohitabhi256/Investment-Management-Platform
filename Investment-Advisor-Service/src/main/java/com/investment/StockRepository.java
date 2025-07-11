package com.investment;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
    List<Stock> findAllById(Iterable<Long> ids);
    
}