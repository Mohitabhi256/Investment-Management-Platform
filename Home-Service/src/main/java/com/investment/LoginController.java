package com.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.investment.EncryptionUtils;
import com.investment.Investor;
import com.investment.InvestorRepository;
import com.investment.InvestorService;


@Controller
public class LoginController {

    @Autowired
    private InvestorService investorService;

    @Autowired
    private AuthService authService;

    @Autowired
    private LoginService ls;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired 
    private InvestorRepository investorRepository;

    @RequestMapping(value = "/login/admin", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView adminLoginPage() {
        return new ModelAndView("loginAdmin"); // return the admin login JSP/HTML page
    }

    @RequestMapping(value = "/login/advisor", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView advisorLoginPage() {
        return new ModelAndView("loginAdvisor"); // return the advisor login JSP/HTML page
    }

    @RequestMapping(value = "/login/investor", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView investorLoginPage() {
        return new ModelAndView("loginInvestor"); // return the investor login JSP/HTML page
    }

    @RequestMapping(value = "/register/investor", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView investorRegisterPage() {
        return new ModelAndView("registerInvestor"); // return the investor registration JSP/HTML page
    }

    @PostMapping("/login/investor")
    @ResponseBody
    public ModelAndView investorLogin(@RequestParam String email, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        if (authService.validateInvestor(email, password)) {
            modelAndView.setViewName(ls.getInvestorDashboard(email));
        } else {
            modelAndView.setViewName("loginInvestor");
            modelAndView.addObject("error", "Invalid credentials");
        }
        return modelAndView;
    }

    @GetMapping("/forgotPassword")
    @ResponseBody
    public ModelAndView forgetPasswordPage() {
        return new ModelAndView("ForgotPassword"); // returns the common page where the user selects the login role
    }
    @GetMapping("/forgotPasswordAdvisor")
    @ResponseBody
    public ModelAndView forgetPasswordAdvisorPage() {
        return new ModelAndView("ForgetPasswordAdvisor"); // returns the common page where the user selects the login role
    }

    @PostMapping("/submitInvestorForgotPassword")
    @ResponseBody
    public ModelAndView forgotPassword(@RequestParam("oldEmail") String oldEmail) {
        ModelAndView modelAndView = new ModelAndView();
        if (ls.findInvestor(oldEmail)) {
            modelAndView.setViewName("OtpPage");
            modelAndView.addObject("investorEmail", oldEmail);
        } else {
            modelAndView.setViewName("errorPage"); // Customize this as needed
        }
        return modelAndView;
    }
    
    @PostMapping("/submitAdvisorForgotPassword")
    @ResponseBody
    public ModelAndView forgotAdvisorPassword(@RequestParam("oldEmail") String oldEmail) {
        ModelAndView modelAndView = new ModelAndView();
        if (ls.findAdvisor(oldEmail)) {
            modelAndView.setViewName("OtpAdvisorPage");
            modelAndView.addObject("advisorEmail", oldEmail);
        } else {
            modelAndView.setViewName("errorPage"); // Customize this as needed
        }
        return modelAndView;
    }
    @PostMapping("/verifyOtp")
    @ResponseBody
    public ResponseEntity<?> validateOtp(@RequestParam("investorEmail") String investorEmail, @RequestParam("otp") String otp) {
        long investorId = investorRepository.findIdByEmail(investorEmail);
        Investor inv = investorRepository.findById(investorId).orElse(null);
        if (otp.equals(inv.getOtp())) {
            String newPassword = PasswordUtil.generatePassword(6);
            try {
                String encryptedP = EncryptionUtils.encrypt(newPassword);
                inv.setPassword(encryptedP);
                investorRepository.save(inv);

                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(investorEmail);
                simpleMailMessage.setSubject("Password Reset");
                simpleMailMessage.setText("Your new password is: " + newPassword);
                javaMailSender.send(simpleMailMessage);

                return ResponseEntity.status(201).body("New password sent to your email ID.");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(404).body("Error occurred");
            }
        } else {
            return ResponseEntity.status(404).body("Incorrect OTP");
        }
    }
    @Autowired
    private AdvisorRepository advisorRepository;
    @PostMapping("/verifyAdvisorOtp")
    @ResponseBody
    public ResponseEntity<?> validateAdvisorOtp(@RequestParam("advisorEmail") String advisorEmail, @RequestParam("otp") String otp) {
    	 Long advisorId = authService.getAdvisorIdByEmail(advisorEmail);
        Advisor ad = advisorRepository.findById(advisorId).orElse(null);
        if (otp.equals(ad.getOtp())) {
            String newPassword = PasswordUtil.generatePassword(6);
            try {
                String encryptedP = EncryptionUtils.encrypt(newPassword);
                ad.setPassword(encryptedP);
                advisorRepository.save(ad);

                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(advisorEmail);
                simpleMailMessage.setSubject("Password Reset");
                simpleMailMessage.setText("Your new password is: " + newPassword);
                javaMailSender.send(simpleMailMessage);

                return ResponseEntity.status(201).body("New password sent to your email ID.");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(404).body("Error occurred");
            }
        } else {
            return ResponseEntity.status(404).body("Incorrect OTP");
        }
    }

    @PostMapping("/register/investor")
    @ResponseBody
    public ModelAndView investorRegister(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        if (investorService.isEmailRegistered(email)) {
            modelAndView.setViewName("registerInvestor");
            modelAndView.addObject("error", "Email already registered.");
        } else {
            boolean registrationSuccess = investorService.registerInvestor(name, email, password);
            if (registrationSuccess) {
                modelAndView.setViewName("loginInvestor");
                modelAndView.addObject("success", "Registration successful. Please log in.");
            } else {
                modelAndView.setViewName("registerInvestor");
                modelAndView.addObject("error", "Registration failed. Please try again.");
            }
        }
        return modelAndView;
    }

    @PostMapping("/login/admin")
    @ResponseBody
    public ModelAndView adminLogin(@RequestParam String email, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        if (authService.validateAdmin(email, password)) {
            modelAndView.setViewName(ls.getAdminDashboard());
        } else {
            modelAndView.setViewName("loginAdmin");
            modelAndView.addObject("error", "Invalid credentials");
        }
        return modelAndView;
    }

    @PostMapping("/login/advisor")
    @ResponseBody
    public ModelAndView advisorLogin(@RequestParam String email, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        if (authService.validateAdvisor(email, password)) {
            Long advisorId = authService.getAdvisorIdByEmail(email);
            modelAndView.setViewName(ls.getAdvisorDashboard(advisorId));
        } else {
            modelAndView.setViewName("loginAdvisor");
            modelAndView.addObject("error", "Invalid credentials");
        }
        return modelAndView;
    }
}

