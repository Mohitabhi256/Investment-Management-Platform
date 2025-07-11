package com.investment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import jakarta.persistence.*;

@Entity
@Table(name = "baskets")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_seq")
    @SequenceGenerator(name = "basket_seq", sequenceName = "basket_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "basket_stocks",
        joinColumns = @JoinColumn(name = "basket_id"),
        inverseJoinColumns = @JoinColumn(name = "stock_id")
    )
    private List<Stock> stocks;

    @ElementCollection
    @CollectionTable(name = "basket_stock_units", joinColumns = @JoinColumn(name = "basket_id"))
    @MapKeyJoinColumn(name = "stock_id")
    @Column(name = "units")
    private Map<Stock, Integer> stockUnits = new HashMap<>();

    private String strategy;
    private BigDecimal totalPrice;
    private String createdBy;

    public Basket() {
        super();
    }

    public Basket(Long id, String name, List<Stock> stocks, Map<Stock, Integer> stockUnits, String strategy, BigDecimal totalPrice, String createdBy) {
        super();
        this.id = id;
        this.name = name;
        this.stocks = stocks;
        this.stockUnits = stockUnits;
        this.strategy = strategy;
        this.totalPrice = totalPrice;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Map<Stock, Integer> getStockUnits() {
        return stockUnits;
    }

    public void setStockUnits(Map<Stock, Integer> stockUnits) {
        this.stockUnits = stockUnits;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Basket [id=" + id + ", name=" + name + ", stocks=" + stocks + ", stockUnits=" + stockUnits + ", strategy=" + strategy + ", totalPrice=" + totalPrice + ", createdBy=" + createdBy + "]";
    }
}
//package com.investment;
//
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "baskets")
//public class Basket {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_seq")
//    @SequenceGenerator(name = "basket_seq", sequenceName = "basket_seq", allocationSize = 1)
//    private Long id;
//
//    @Column(unique = true)
//    private String name;
//
//    @ManyToMany
//    @JoinTable(
//        name = "basket_stocks",
//        joinColumns = @JoinColumn(name = "basket_id"),
//        inverseJoinColumns = @JoinColumn(name = "stock_id")
//    )
//    private List<Stock> stocks;
//
//    private String strategy;
//    private BigDecimal totalPrice;
//	private String createdBy;
//	public Basket() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public Basket(Long id, String name, List<Stock> stocks, String strategy, BigDecimal totalPrice, String createdBy) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.stocks = stocks;
//		this.strategy = strategy;
//		this.totalPrice = totalPrice;
//		this.createdBy = createdBy;
//	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public List<Stock> getStocks() {
//		return stocks;
//	}
//	public void setStocks(List<Stock> stocks) {
//		this.stocks = stocks;
//	}
//	public String getStrategy() {
//		return strategy;
//	}
//	public void setStrategy(String strategy) {
//		this.strategy = strategy;
//	}
//	public BigDecimal getTotalPrice() {
//		return totalPrice;
//	}
//	public void setTotalPrice(BigDecimal totalPrice) {
//		this.totalPrice = totalPrice;
//	}
//	public String getCreatedBy() {
//		return createdBy;
//	}
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//	@Override
//	public String toString() {
//		return "Basket [id=" + id + ", name=" + name + ", stocks=" + stocks + ", strategy=" + strategy + ", totalPrice="
//				+ totalPrice + ", createdBy=" + createdBy + "]";
//	}
//    
//
//    // Getters and setters
//}
