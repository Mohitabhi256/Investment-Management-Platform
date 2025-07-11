package com.investment;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Advisor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advisor_seq")
    @SequenceGenerator(name = "advisor_seq", sequenceName = "advisor_seq", allocationSize = 1)
    private Long advisorId;

    private String name;
    private String email;
    private String password;
    private String otp;
	public Advisor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Advisor(Long advisorId, String name, String email, String password, String otp) {
		super();
		this.advisorId = advisorId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.otp = otp;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Long getAdvisorId() {
		return advisorId;
	}
	public void setAdvisorId(Long advisorId) {
		this.advisorId = advisorId;
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
	@Override
	public String toString() {
		return "Advisor [advisorId=" + advisorId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", otp=" + otp + "]";
	}

    // Getters and setters
}
