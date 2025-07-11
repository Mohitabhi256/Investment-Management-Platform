package com.investment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByInvestor(Investor investor);
}
