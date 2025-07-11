package com.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private StockRepository stockRepository;

    public Basket createBasket(String name, Map<Long, Integer> stockUnits, String strategy, String createdBy) {
        List<Stock> stocks = stockRepository.findAllById(stockUnits.keySet());
        BigDecimal totalPrice = stocks.stream()
                                      .map(stock -> stock.getClosingPrice().multiply(BigDecimal.valueOf(stockUnits.get(stock.getId()))))
                                      .reduce(BigDecimal.ZERO, BigDecimal::add);

        Basket basket = new Basket();
        basket.setName(name);
        basket.setStocks(stocks);
        basket.setStrategy(strategy);
        basket.setTotalPrice(totalPrice);
        basket.setCreatedBy(createdBy);
        basket.setStockUnits(stocks.stream().collect(Collectors.toMap(stock -> stock, stock -> stockUnits.get(stock.getId()))));

        return basketRepository.save(basket);
    }
    public List<Basket> getBasketsByAdvisorName(String advisorName) {
        return basketRepository.findByCreatedBy(advisorName);
    }

    public void updateBasketPrices() {
        List<Basket> baskets = basketRepository.findAll();
        for (Basket basket : baskets) {
            BigDecimal totalPrice = basket.getStocks().stream()
                                          .map(Stock::getClosingPrice)
                                          .reduce(BigDecimal.ZERO, BigDecimal::add);
            basket.setTotalPrice(totalPrice);
            basketRepository.save(basket);
        }
    }
    public List<Basket> getBasketsByCreator(String createdBy) {
        return basketRepository.findByCreatedBy(createdBy);
    }
    public Basket getBasketById(Long id) {
        Optional<Basket> basket = basketRepository.findById(id);
        return basket.orElse(null);
    }

    public List<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }
}
