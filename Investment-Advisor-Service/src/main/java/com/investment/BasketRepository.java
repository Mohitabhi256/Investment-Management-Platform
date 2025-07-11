package com.investment;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
	List<Basket> findByCreatedBy(String advisorName);
//	List<Basket> findByCreatedBy(Long advisorId);
}
