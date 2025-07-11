package com.investment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investment.Basket;
import com.investment.BasketRepository;

@Service
public class InvestmentService {
    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @Autowired
    private BasketRepository basketRepository;

    public void investInBasket(Long investorId, Long basketId, BigDecimal amount) {
        Investor investor = investorRepository.findById(investorId).orElseThrow();
        Basket basket = basketRepository.findById(basketId).orElseThrow();

        if (investor.getCashBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        Investment investment = new Investment();
        investment.setInvestor(investor);
        investment.setBasket(basket);
        investment.setAmountInvested(amount);
        investment.setCurrentValue(amount); // Initially, current value is the same as invested amount
        investment.setInvestmentDate(LocalDateTime.now()); // Set the investment date

        investor.setCashBalance(investor.getCashBalance().subtract(amount));
        investorRepository.save(investor);
        investmentRepository.save(investment);
    }

    public List<Investment> getInvestmentsByInvestor(Long investorId) {
        Investor investor = investorRepository.findById(investorId).orElseThrow();
        return investmentRepository.findByInvestor(investor);
    }
    public void redeemInvestment(Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId).orElseThrow();
        Investor investor = investment.getInvestor();

        BigDecimal currentValue = investment.getCurrentValue();
        investor.setCashBalance(investor.getCashBalance().add(currentValue));

        investmentRepository.delete(investment);
        investorRepository.save(investor);
    }
}
