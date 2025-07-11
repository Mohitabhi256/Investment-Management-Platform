package com.investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    boolean existsByEmail(String email);
    Investor findByEmail(String email);
    @Query("SELECT i.Id FROM Investor i WHERE i.email = :email")
    public long findIdByEmail(@Param("email") String email);
}
