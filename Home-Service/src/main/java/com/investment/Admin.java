package com.investment;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(Long adminId, String username, String email, String password, LocalDateTime createdAt) {
		super();
		this.adminId = adminId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
	}
	public Long getAdminId() {
		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", createdAt=" + createdAt + "]";
	}

    // Getters and setters
    
}
