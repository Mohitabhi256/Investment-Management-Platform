package com.investment;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
//
//@Entity
//@Table(name = "investors")
//public class Investor {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "investor_seq")
//    @SequenceGenerator(name = "investor_seq", sequenceName = "investor_seq", allocationSize = 1)
//    private Long investorId;
//
//    private String name;
//
//    @Column(unique = true)
//    private String email;
//
//    private String password;
//
//    private BigDecimal walletBalance = BigDecimal.ZERO;
//
//    @ManyToMany
//    @JoinTable(
//        name = "investor_baskets",
//        joinColumns = @JoinColumn(name = "investor_id"),
//        inverseJoinColumns = @JoinColumn(name = "basket_id")
//    )
//    private List<Basket> basketsBought;
//
//    // Constructors, getters, and setters
//
//    public Investor() {}
//
//    public Investor(String name, String email, String password) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//    public Long getInvestorId() {
//        return investorId;
//    }
//
//    public void setInvestorId(Long investorId) {
//        this.investorId = investorId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public BigDecimal getWalletBalance() {
//        return walletBalance;
//    }
//
//    public void setWalletBalance(BigDecimal walletBalance) {
//        this.walletBalance = walletBalance;
//    }
//
//    public List<Basket> getBasketsBought() {
//        return basketsBought;
//    }
//
//    public void setBasketsBought(List<Basket> basketsBought) {
//        this.basketsBought = basketsBought;
//    }
//}

@Entity
@Table(name = "investor")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "investor_seq")
    @SequenceGenerator(name = "investor_seq", sequenceName = "investor_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    private String password;
    private BigDecimal cashBalance;
    private String otp;
	public Investor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Investor(Long id, String name, String email, String password, BigDecimal cashBalance,String otp) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.cashBalance = cashBalance;
		this.otp = otp;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BigDecimal getCashBalance() {
		return cashBalance;
	}
	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	@Override
	public String toString() {
		return "Investor [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", cashBalance=" + cashBalance + "]";
	}

    // Getters and Setters
}

