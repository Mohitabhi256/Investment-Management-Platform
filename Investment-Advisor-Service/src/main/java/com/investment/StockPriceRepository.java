package com.investment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    List<StockPrice> findBySymbol(String symbol);
    Optional<StockPrice> findBySymbolAndSdate(String symbol, Date sdate);
}