package com.investment;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.Tuple;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
    Advisor findByEmail(String email);
    Advisor findNameByEmail(String email);
    @Query("SELECT i.Id FROM Advisor i WHERE i.email = :email")
    public Tuple findIdByEmail(@Param("email") String email);
	//String getNameByEmail(String email);
    @Query("SELECT a.name, a.email FROM Advisor a WHERE a.email = :email")
    Tuple findAdvisorDetailsByEmail(@Param("email") String email);
}
