package com.investment;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.investment.Investor;
import com.investment.InvestorRepository;

@Service
public class LoginService {

	@Autowired
    private RestTemplate restTemplate;

	@Autowired
    private DiscoveryClient discoveryClient;
	
	@Autowired
	JavaMailSender javaMailSender;
//	public  AdminController getAdminDashboard() {
//	    // Remote method call to Admin-Service
//	    // Using RestTemplate to access the Admin dashboard
//	    String adminServiceURL = "http://Admin-Service/dashboard";
//
//	    // Assuming you have a RestTemplate bean configured
//	    RestTemplate restTemplate = new RestTemplate();
//	    
//	    // Make the GET request to the Admin-Service and return the Admin dashboard
//	    return restTemplate.getForObject(adminServiceURL, AdminController.class);
//	}
    public String getAdminDashboard() {
        try {
            List<ServiceInstance> instances = discoveryClient.getInstances("Admin-Service");
            if (!instances.isEmpty()) {
                ServiceInstance serviceInstance = instances.get(0);
                String adminServiceURL = serviceInstance.getUri().toString() + "/admindashboard";
                return "redirect:" + adminServiceURL;
            } else {
                return "redirect:/login/admin";
            }
        } 
        catch (RestClientException e) {
            // Handle the exception (e.g., log it, rethrow it, return a default value, etc.)
            System.err.println("Error occurred while calling Admin-Service: " + e.getMessage());
            return null;
        }
    }
    
    
    
    
    
	
	public String getAdvisorDashboard(Long advisorId) {
	    try {
	        List<ServiceInstance> instances = discoveryClient.getInstances("ADVISORMS");
	        if (!instances.isEmpty()) {
	            ServiceInstance serviceInstance = instances.get(0);
	            String advisorServiceURL = serviceInstance.getUri().toString() + "/advisordashboard/" + advisorId;
	            return "redirect:" + advisorServiceURL;
	        } else {
	            return "redirect:/login/advisor";
	        }
	    } 
	    catch (RestClientException e) {
	        // Handle the exception (e.g., log it, rethrow it, return a default value, etc.)
	        System.err.println("Error occurred while calling AdvisorMS: " + e.getMessage());
	        return null;
	    }
	}

	public String getInvestorDashboard(String email) {
		try {
            List<ServiceInstance> instances = discoveryClient.getInstances("Investor-Service");
            if (!instances.isEmpty()) {
                ServiceInstance serviceInstance = instances.get(0);
                String investorServiceURL = serviceInstance.getUri().toString() + "/investorDashboard/" + email;
                return "redirect:" + investorServiceURL;
            } else {
                return "redirect:/login/investor";
            }
        } 
        catch (RestClientException e) {
            // Handle the exception (e.g., log it, rethrow it, return a default value, etc.)
            System.err.println("Error occurred while calling Investor-Service: " + e.getMessage());
            return null;
        }
	}
	@Autowired
	private InvestorRepository investorRepository;
	
	public boolean findInvestor(String email) {
		   Boolean sentEmail=false;
	   	long investorId = investorRepository.findIdByEmail(email);
	   	Investor inv = investorRepository.findById(investorId).orElse(null);
	   	if(investorId != 0) {
	   		System.out.println(investorId);
	   		String otp = OtpUtil.generateOtp();
	   		inv.setOtp(otp);
	   		investorRepository.save(inv);
	   		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	 		  simpleMailMessage.setTo(email);
	 		  simpleMailMessage.setSubject("OTP for verificatio");
	 		  simpleMailMessage.setText("OTP is: "+otp);
	 		  javaMailSender.send(simpleMailMessage);
	 		  sentEmail = true;
	   		return sentEmail;
	   	}
	   	else {
	   		return sentEmail;
	   	}
	 }



	@Autowired
	private AdvisorRepository advisorRepository;
	@Autowired
	private AuthService authService;

	public boolean findAdvisor(String email) {
		   Boolean sentEmail=false;
		   Long advisorId = authService.getAdvisorIdByEmail(email);
		   	Advisor ad = advisorRepository.findById(advisorId).orElse(null);
		   	if(advisorId != 0) {
		   		System.out.println(advisorId);
		   		String otp = OtpUtil.generateOtp();
		   		ad.setOtp(otp);
		   		advisorRepository.save(ad);
		   		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		 		  simpleMailMessage.setTo(email);
		 		  simpleMailMessage.setSubject("OTP for verificatio");
		 		  simpleMailMessage.setText("OTP is: "+otp);
		 		  javaMailSender.send(simpleMailMessage);
		 		  sentEmail = true;
		   		return sentEmail;
		   	}
		   	else {
		   		return sentEmail;
		   	}
	}










}
