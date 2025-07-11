package com.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockPriceService {
    @Autowired
    private StockPriceRepository stockPriceRepository;

    public BigDecimal getStockPriceOnDate(String symbol, LocalDate date) {
        Optional<StockPrice> stockPriceOptional = stockPriceRepository.findBySymbolAndSdate(symbol, java.sql.Date.valueOf(date));
        if (stockPriceOptional.isPresent()) {
            return stockPriceOptional.get().getPrice();
        }
        throw new IllegalArgumentException("No historical price found for stock: " + symbol + " on date: " + date);
    }
}
