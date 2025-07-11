package com.investment;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.Tuple;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
    Advisor findByEmail(String email);
    String findNameByEmail(String email);
    @Query("SELECT a.name, a.email FROM Advisor a WHERE a.email = :email")
    Tuple findAdvisorDetailsByEmail(@Param("email") String email);
    Advisor findByName(String name);
    Advisor findByAdvisorId(Long advisorId);
}
