package com.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockPriceRepository stockPriceRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

    public List<StockPrice> getStockPricesBySymbol(String symbol) {
        return stockPriceRepository.findBySymbol(symbol);
    } 
    public void saveStock(Stock stock) {
        if (!stockRepository.existsBySymbol(stock.getSymbol())) {
            stockRepository.save(stock);
        }
    }

    public List<Stock> getAllStocksByIds(Set<Long> ids) {
        return stockRepository.findAllById(ids);
    }

	

}
