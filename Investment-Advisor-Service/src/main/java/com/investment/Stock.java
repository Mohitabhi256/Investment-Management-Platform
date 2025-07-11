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
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_seq")
    @SequenceGenerator(name = "stock_seq", sequenceName = "stock_seq", allocationSize = 1)
    private Long id;
    private String symbol;
    private String companyName;
    private String industry;
    private String series;
    private String isinCode;
    private BigDecimal closingPrice;
    private BigDecimal averagePrice;
    private BigDecimal highestPrice;
    private BigDecimal lowestPrice;
    private BigDecimal percentageChange;
    private Date sdate;
    
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Stock(Long id,String symbol, String companyName, String industry, String series, String isinCode,
			BigDecimal closingPrice, BigDecimal averagePrice, BigDecimal highestPrice, BigDecimal lowestPrice,
			BigDecimal percentageChange, Date sdate) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.companyName = companyName;
		this.industry = industry;
		this.series = series;
		this.isinCode = isinCode;
		this.closingPrice = closingPrice;
		this.averagePrice = averagePrice;
		this.highestPrice = highestPrice;
		this.lowestPrice = lowestPrice;
		this.percentageChange = percentageChange;
		this.sdate = sdate;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getIsinCode() {
		return isinCode;
	}
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}
	public BigDecimal getClosingPrice() {
		return closingPrice;
	}
	public void setClosingPrice(BigDecimal closingPrice) {
		this.closingPrice = closingPrice;
	}
	public BigDecimal getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}
	public BigDecimal getHighestPrice() {
		return highestPrice;
	}
	public void setHighestPrice(BigDecimal highestPrice) {
		this.highestPrice = highestPrice;
	}
	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public BigDecimal getPercentageChange() {
		return percentageChange;
	}
	public void setPercentageChange(BigDecimal percentageChange) {
		this.percentageChange = percentageChange;
	}
	public Date getSDate() {
		return sdate;
	}
	public void setSDate(Date sdate) {
		this.sdate = sdate;
	}
	@Override
	public String toString() {
		return "Stock [id=" + id + ", symbol=" + symbol + ", companyName=" + companyName + ", industry=" + industry
				+ ", series=" + series + ", isinCode=" + isinCode + ", closingPrice=" + closingPrice + ", averagePrice="
				+ averagePrice + ", highestPrice=" + highestPrice + ", lowestPrice=" + lowestPrice
				+ ", percentageChange=" + percentageChange + ", sdate=" + sdate + "]";
	}

}
