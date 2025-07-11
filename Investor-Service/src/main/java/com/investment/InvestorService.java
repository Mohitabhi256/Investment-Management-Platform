package com.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvestorService {
    @Autowired
    private InvestorRepository investorRepository;

    public boolean validateInvestor(String email, String password) {
        Investor investor = investorRepository.findByEmail(email);
        
		try {
			 String encPass = EncryptionUtils.encrypt(password);
			return investor != null && (investor.getPassword().equals(encPass)||investor.getPassword().equals(password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
        
    }

    public boolean isEmailRegistered(String email) {
        return investorRepository.findByEmail(email) != null;
    }

    public boolean registerInvestor(String name, String email, String password) {
        if (isEmailRegistered(email)) {
            return false;
        }
        Investor investor = new Investor();
        investor.setName(name);
        investor.setEmail(email);
        investor.setPassword(password);
        investor.setCashBalance(BigDecimal.ZERO);
        investorRepository.save(investor);
        return true;
    }

    public String resetPassword(String email) {
        Investor investor = investorRepository.findByEmail(email);
        if (investor == null) {
            return null;
        }
        String newPassword = generateRandomPassword();
        investor.setPassword(newPassword);
        investorRepository.save(investor);
        // Send newPassword to investor's email
        return newPassword;
    }

   

    public void addFunds(Long id, BigDecimal amount) {
        Investor investor = investorRepository.findById(id).orElseThrow();
        investor.setCashBalance(investor.getCashBalance().add(amount));
        investorRepository.save(investor);
    }

    public void withdrawFunds(Long id, BigDecimal amount) {
        Investor investor = investorRepository.findById(id).orElseThrow();
        if (investor.getCashBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        investor.setCashBalance(investor.getCashBalance().subtract(amount));
        investorRepository.save(investor);
    }

    private String generateRandomPassword() {
        // Implement random password generation logic
        return "newRandomPassword";
    }

    public Investor findByEmail(String email) {
        return investorRepository.findByEmail(email);
    }
    public ResponseEntity<?> emailUpdate(String oldEmail,String newEmail,String password){
 	   try {
	 	   long investorId = investorRepository.findIdByEmail(oldEmail);
	 	   Investor inv = investorRepository.findById(investorId).orElse(null);
	 	   String decpass = EncryptionUtils.decrypt(inv.getPassword());
	 	   if(password.equals(decpass)) {
		 	   inv.setEmail(newEmail);
		 	   investorRepository.save(inv);
		 	   return ResponseEntity.status(201).body("email updated successfully");
	 	   }else {
	 		   return ResponseEntity.status(404).body("Authorization Failed");
	 	   }
 	   }catch(Exception e) {
 		   return ResponseEntity.status(404).body(e);
 	   }
    }
    public ResponseEntity<?> passwordUpdate(String email,String oldPassword,String newPassword){
 	   try {
	 	   long investorId = investorRepository.findIdByEmail(email);
	 	   Investor inv = investorRepository.findById(investorId).orElse(null);
	 	   String decpass = EncryptionUtils.decrypt(inv.getPassword());
	 	   String encryptPass = EncryptionUtils.encrypt(newPassword);
	 	   if(oldPassword.equals(decpass)) {
	 		   inv.setPassword(encryptPass);;
	 		   investorRepository.save(inv);
	 		   return ResponseEntity.status(201).body("password updated successfully");
	 	   }else {
	 		   return ResponseEntity.status(404).body("Authorization Failed");
 	   }
 	   }catch(Exception e) {
 		   return ResponseEntity.status(404).body(e);
 	   }
    }
}
