package com.investment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionTest implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseConnectionTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
        	jdbcTemplate.execute("SELECT 1 FROM DUAL");
            System.out.println("Database connection is successful!");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}
