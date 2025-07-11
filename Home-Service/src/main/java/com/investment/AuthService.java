package com.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.investment.EncryptionUtils;

@Service
public class AuthService {

//	@Autowired
//    private JdbcTemplateAutoConfiguration jdbcTemplate;
//
   @Autowired
    private AdminRepository adminRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;

   public boolean validateAdmin(String email, String rawPassword) {
	    System.out.println("Checking admin details for email: " + email);
	    Admin admin = adminRepository.findByEmail(email);
	    if (admin != null) {
	        System.out.println("Admin found: " + admin.getEmail());
	        boolean isMatch = passwordEncoder.matches(rawPassword, admin.getPassword());
	        if (isMatch) {
	            System.out.println("Login successful");
	            return true;
	        } else {
	            System.out.println("Oops....!Details not matching ");
	            return false;
	        }
	    } else {
	        System.out.println("Oops....!Email not matching ");
	        return false;
	    }
	}

   @Autowired
   private AdvisorRepository advisorRepository;

   public boolean validateAdvisor(String email, String password) {
       Advisor advisor = advisorRepository.findByEmail(email);
       
		try {
			 String encPass = EncryptionUtils.encrypt(password);
			return advisor != null && advisor.getPassword().equals(encPass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
       
   }
   public Long getAdvisorIdByEmail(String email) {
       Advisor advisor = advisorRepository.findByEmail(email);
       if (advisor != null) {
           return advisor.getAdvisorId();
       } else {
           // Handle the case where the advisor is not found
           throw new RuntimeException("Advisor not found with email: " + email);
       }
   }

    public boolean isEmailRegistered(String email) {
//        
    	return false;
    }
    public boolean registerInvestor(String name, String email, String password) {

    	return true;
    }

	public boolean validateInvestor(String email, String password) {
		// TODO Auto-generated method stub
		return true;
	}
}
   