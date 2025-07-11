package com.investment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.investment.Basket;

import jakarta.persistence.*;

@Entity
@Table(name = "investment")
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "investment_seq")
    @SequenceGenerator(name = "investment_seq", sequenceName = "investment_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Investor investor;

    @ManyToOne
    private Basket basket;

    private BigDecimal amountInvested;
    private BigDecimal currentValue;
    private LocalDateTime investmentDate; // Add this line
	public Investment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Investment(Long id, Investor investor, Basket basket, BigDecimal amountInvested, BigDecimal currentValue, LocalDateTime investmentDate) {
        super();
        this.id = id;
        this.investor = investor;
        this.basket = basket;
        this.amountInvested = amountInvested;
        this.currentValue = currentValue;
        this.investmentDate = investmentDate; // Add this line
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public BigDecimal getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(BigDecimal amountInvested) {
        this.amountInvested = amountInvested;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public LocalDateTime getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(LocalDateTime investmentDate) {
        this.investmentDate = investmentDate;
    }

    @Override
    public String toString() {
        return "Investment [id=" + id + ", investor=" + investor + ", basket=" + basket + ", amountInvested="
                + amountInvested + ", currentValue=" + currentValue + ", investmentDate=" + investmentDate + "]";
    }
}