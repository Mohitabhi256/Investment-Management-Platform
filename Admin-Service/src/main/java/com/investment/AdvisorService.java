package com.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.Tuple;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvisorService {

    @Autowired
    private AdvisorRepository advisorRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Advisor addAdvisor(Advisor advisor) {
        try {
			advisor.setPassword(EncryptionUtils.encrypt(advisor.getPassword()));
			return advisorRepository.save(advisor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
    }

    public void removeAdvisor(Long advisorId) {
        advisorRepository.deleteById(advisorId);
    }

    public List<Advisor> viewAdvisors() {
    	List<Advisor> advisorList = new ArrayList<>();
    	advisorList = advisorRepository.findAll();
    	System.out.println(advisorList);
        return advisorRepository.findAll();
    }

    public String getAdvisorNameByEmail(String email) {
        Tuple result = advisorRepository.findAdvisorDetailsByEmail(email);
        return result != null ? result.get(0, String.class) : null;
    }

    public String getAdvisorNameById(Long id) {
        Advisor advisor = advisorRepository.findById(id).orElseThrow(() -> new RuntimeException("Advisor not found"));
        return advisor.getName();
    }

	public ResponseEntity<?> emailUpdate(String oldEmail,String newEmail,String password){
 	   try {
	 	   long advisorId = advisorRepository.findByEmail(oldEmail).getAdvisorId();
	 	   Advisor inv = advisorRepository.findByAdvisorId(advisorId);
	 	   String decpass = EncryptionUtils.decrypt(inv.getPassword());
	 	   if(password.equals(decpass)) {
		 	   inv.setEmail(newEmail);
		 	   advisorRepository.save(inv);
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
	 	   long advisorId = advisorRepository.findByEmail(email).getAdvisorId();
	 	   Advisor inv = advisorRepository.findByAdvisorId(advisorId);
	 	   String decpass = EncryptionUtils.decrypt(inv.getPassword());
	 	   String encryptPass = EncryptionUtils.encrypt(newPassword);
	 	   if(oldPassword.equals(decpass)) {
	 		   inv.setPassword(encryptPass);;
	 		   advisorRepository.save(inv);
	 		   return ResponseEntity.status(201).body("password updated successfully");
	 	   }else {
	 		   return ResponseEntity.status(404).body("Authorization Failed");
 	   }
 	   }catch(Exception e) {
 		   return ResponseEntity.status(404).body(e);
 	   }
    }
}
