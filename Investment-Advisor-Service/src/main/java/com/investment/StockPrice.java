package com.investment;


import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock_prices")

public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_prices_seq")
    @SequenceGenerator(name = "stock_prices_seq", sequenceName = "stock_prices_seq", allocationSize = 1)
    private Long id;
    private String symbol;
    private Date sdate;
    private BigDecimal price;
	public StockPrice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StockPrice(Long id, String symbol, Date sdate, BigDecimal price) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.sdate = sdate;
		this.price = price;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "StockPrice [id=" + id + ", symbol=" + symbol + ", sdate=" + sdate + ", price=" + price + "]";
	}

    // Getters and setters
}
