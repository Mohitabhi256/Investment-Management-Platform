package com.investment;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.investment.Basket;
import com.investment.BasketService;
import com.investment.Stock;
import com.investment.StockPriceService;

@Controller
public class InvestorController {

    @Autowired
    private InvestorService investorService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private InvestmentService investmentService;
    
    @Autowired
    private StockPriceService stockPriceService;
    @Autowired 
    private InvestmentRepository investmentRepository;
    @Autowired 
    private InvestorRepository investorRepository;
	  
    @GetMapping("/investorDashboard/{email}")
    @ResponseBody
    public ModelAndView investorDashboardPage(@PathVariable String email) {
        Investor investor = investorService.findByEmail(email);
        ModelAndView model = new ModelAndView("investorDashboard");
        model.addObject("investorId", investor.getId());
        model.addObject("email", investor.getEmail());
        return model;
    }

    @GetMapping("/updateProfile/{email}")
    @ResponseBody
    public ModelAndView updateProfilePage(@PathVariable("email") String email) {
        System.out.println("Investor email to update profile: " + email);
        ModelAndView model = new ModelAndView("updateProfile");
        model.addObject("investorEmail", email);
        return model;
    }

    @GetMapping("/updateEmail/{email}")
    @ResponseBody
    public ModelAndView updateEmailPage(@PathVariable("email") String email) {
        System.out.println("Investor email to update email: " + email);
        ModelAndView model = new ModelAndView("updateEmail");
        model.addObject("investorEmail", email);
        return model;
    }

    @GetMapping("/updatePassword/{email}")
    @ResponseBody
    public ModelAndView updatePasswordPage(@PathVariable("email") String email) {
        System.out.println("Investor email to update password: " + email);
        ModelAndView model = new ModelAndView("updatePassword");
        model.addObject("investorEmail", email);
        return model;
    }

    @PatchMapping("/submitInvestorEmailUpdate")
    @ResponseBody
    public ResponseEntity<?> investorEmailUpdate(@RequestParam("investorEmail") String investorEmail,
                                                 @RequestParam("newEmail") String newEmail,
                                                 @RequestParam("investorPassword") String investorPassword) {
        return investorService.emailUpdate(investorEmail, newEmail, investorPassword);
    }

    @PatchMapping("/submitInvestorPasswordUpdate")
    @ResponseBody
    public ResponseEntity<?> investorPasswordUpdate(@RequestParam("investorEmail") String investorEmail,
                                                    @RequestParam("oldPassword") String oldPassword,
                                                    @RequestParam("newPassword") String newPassword) {
        return investorService.passwordUpdate(investorEmail, oldPassword, newPassword);
    }

    @PostMapping("/resetPassword")
    @ResponseBody
    public ModelAndView resetPassword(@RequestParam String email) {
        ModelAndView model = new ModelAndView("loginInvestor");
        String newPassword = investorService.resetPassword(email);
        if (newPassword != null) {
            model.addObject("success", "New password sent to your email.");
        } else {
            model.addObject("error", "Email not found.");
        }
        return model;
    }

    @GetMapping("/addFunds/{id}")
    @ResponseBody
    public ModelAndView showAddFundsPage(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("addFunds");
        model.addObject("investorId", id);
        return model;
    }

    @GetMapping("/withdrawFunds/{id}")
    @ResponseBody
    public ModelAndView showWithdrawFundsPage(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("withdrawFunds");
        model.addObject("investorId", id);
        return model;
    }

    @PostMapping("/addFunds/{id}")
    @ResponseBody
    public ModelAndView addFunds(@PathVariable Long id, @RequestParam BigDecimal amount) {
        ModelAndView model;
        try {
            investorService.addFunds(id, amount);
            model = new ModelAndView("redirect:/viewBaskets/" + id);
        } catch (Exception e) {
            model = new ModelAndView("wallet");
            model.addObject("error", "Error adding funds: " + e.getMessage());
        }
        return model;
    }

    @PostMapping("/withdrawFunds/{id}")
    @ResponseBody
    public ModelAndView withdrawFunds(@PathVariable Long id, @RequestParam BigDecimal amount) {
        ModelAndView model;
        try {
            investorService.withdrawFunds(id, amount);
            model = new ModelAndView("redirect:/viewBaskets/" + id);
            model.addObject("success", "Funds withdrawn successfully.");
        } catch (IllegalArgumentException e) {
            model = new ModelAndView("viewBaskets");
            model.addObject("error", e.getMessage());
        }
        return model;
    }

    @GetMapping("/viewBaskets/{investorId}")
    @ResponseBody
    public ModelAndView viewBaskets(@PathVariable Long investorId) {
        ModelAndView model = new ModelAndView("viewBaskets");
        List<Basket> baskets = basketService.getAllBaskets();
        model.addObject("baskets", baskets);
        model.addObject("investorId", investorId);
        return model;
    }

    @GetMapping("/viewBasketsByAdvisor/{investorId}/{advisorName}")
    @ResponseBody
    public ModelAndView viewBasketsByAdvisor(@PathVariable Long investorId, @PathVariable String advisorName) {
        String decodedAdvisorName = URLDecoder.decode(advisorName, StandardCharsets.UTF_8);
        System.out.println("Decoded Advisor Name: " + decodedAdvisorName);
        List<Basket> baskets = basketService.getBasketsByAdvisor(decodedAdvisorName);
        ModelAndView model = new ModelAndView("viewBasketByAdvisor");
        model.addObject("baskets", baskets);
        model.addObject("investorId", investorId);
        model.addObject("advisorName", decodedAdvisorName);
        return model;
    }

    @GetMapping("/viewBasketDetails/{investorId}/{basketId}")
    @ResponseBody
    public ModelAndView viewBasketDetails(@PathVariable Long investorId, @PathVariable Long basketId) {
        Basket basket = basketService.getBasketById(basketId);
        ModelAndView model = new ModelAndView("basketDetails");
        model.addObject("basket", basket);
        model.addObject("investorId", investorId);
        return model;
    }

    @PostMapping("/investInBasket/{investorId}/{basketId}")
    @ResponseBody
    public ModelAndView investInBasket(@PathVariable Long investorId, @PathVariable Long basketId, @RequestParam int units) {
        ModelAndView model;
        try {
            BigDecimal totalPrice = basketService.getBasketById(basketId).getTotalPrice();
            BigDecimal totalInvestment = totalPrice.multiply(BigDecimal.valueOf(units));
            investmentService.investInBasket(investorId, basketId, totalInvestment);
            model = new ModelAndView("redirect:/viewInvestments/" + investorId);
            model.addObject("success", "Investment successful.");
        } catch (IllegalArgumentException e) {
            model = new ModelAndView("redirect:/addFunds/" + investorId);
            model.addObject("error", e.getMessage());
        }
        return model;
    }
   

    @PostMapping("/redeemInvestment/{investmentId}")
    @ResponseBody
    public ModelAndView redeemInvestment(@PathVariable Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid investment ID: " + investmentId));
        investmentService.redeemInvestment(investmentId);
        ModelAndView model = new ModelAndView("redirect:/viewInvestments/" + investment.getInvestor().getId());
        model.addObject("success", "Investment redeemed successfully.");
        return model;
    }

    @GetMapping("/viewInvestments/{investorId}")
    @ResponseBody
    public ModelAndView viewInvestments(@PathVariable Long investorId) {
        List<Investment> investments = investmentService.getInvestmentsByInvestor(investorId);
        ModelAndView model = new ModelAndView("viewInvestments");
        model.addObject("investments", investments);
        model.addObject("investorId", investorId);
        return model;
    }

    @GetMapping("/viewInvestedBasketDetails/{investorId}/{investmentId}")
    @ResponseBody
    public ModelAndView viewInvestedBasketDetails(@PathVariable Long investorId, @PathVariable Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid investment ID: " + investmentId));
        Basket basket = investment.getBasket();
        BigDecimal units = investment.getAmountInvested().divide(basket.getTotalPrice(), RoundingMode.HALF_UP);

        // Fetch stock prices on the investment day and current day
        Map<Stock, BigDecimal> investmentDayPrices = new HashMap<>();
        Map<Stock, BigDecimal> currentDayPrices = new HashMap<>();
        for (Stock stock : basket.getStocks()) {
            LocalDate dayOfInvestment = investment.getInvestmentDate().toLocalDate();
            BigDecimal investmentDayPrice = stockPriceService.getStockPriceOnDate(stock.getSymbol(), dayOfInvestment.minusDays(11));
            BigDecimal currentDayPrice = stock.getClosingPrice(); // Assuming closingPrice is the current price

            investmentDayPrices.put(stock, investmentDayPrice);
            currentDayPrices.put(stock, currentDayPrice);
        }

        ModelAndView model = new ModelAndView("investedBasketDetails");
        model.addObject("basket", basket);
        model.addObject("units", units);
        model.addObject("investmentDayPrices", investmentDayPrices);
        model.addObject("currentDayPrices", currentDayPrices);
        model.addObject("investmentId", investmentId);
        model.addObject("investorId", investorId);
        return model;
    }
    
    @GetMapping("/viewPortfolio/{investorId}")
    @ResponseBody
    public ModelAndView viewPortfolio(@PathVariable Long investorId) {
        Investor investor = investorRepository.findById(investorId).orElseThrow(() -> new IllegalArgumentException("Invalid investor ID: " + investorId));
        List<Investment> investments = investmentRepository.findByInvestor(investor);

        BigDecimal totalAmountInvested = BigDecimal.ZERO;
        BigDecimal totalCurrentValue = BigDecimal.ZERO;

        for (Investment investment : investments) {
            totalAmountInvested = totalAmountInvested.add(investment.getAmountInvested());
            totalCurrentValue = totalCurrentValue.add(investment.getCurrentValue());
        }

        // Create a new ModelAndView object
        ModelAndView modelAndView = new ModelAndView("viewPortfolio");
        
        // Add attributes to the model
        modelAndView.addObject("investor", investor);
        modelAndView.addObject("investments", investments);
        modelAndView.addObject("totalAmountInvested", totalAmountInvested);
        modelAndView.addObject("totalCurrentValue", totalCurrentValue);
        
        return modelAndView;
    }
}











































//    @GetMapping("/viewPortfolio/{investorId}")
//    public ModelAndView viewPortfolio(@PathVariable Long investorId) {
//        BigDecimal totalInvestment = investmentService.getTotalInvestment(investorId);
//        BigDecimal currentValue = investmentService.getCurrentValue(investorId);
//        ModelAndView model = new ModelAndView("viewPortfolio");
//        model.addObject("totalInvestment", totalInvestment);
//        model.addObject("currentValue", currentValue);
//        return model;
//    }

//package com.investment;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class InvestorController {
//    @Autowired
//    private InvestorService investorService;
//
//    @Autowired
//    private BasketService basketService;
//
//    @Autowired
//    private InvestmentService investmentService;
//
//    @GetMapping("/investorDashboard/{email}")
//    public String investorDashboardPage(@PathVariable String email, Model model) {
//        Investor investor = investorService.findByEmail(email);
//        model.addAttribute("investorId", investor.getId());
//        model.addAttribute("email", investor.getEmail());
//        return "investorDashboard";
//    }
//    @GetMapping("/updateProfile/{email}")
//    public ModelAndView updateProfilePage(@PathVariable("email")String email) {
// 	   System.out.println("investor email to update email : "+email);
// 	   ModelAndView model = new ModelAndView("updateProfile");
//  	  model.addObject("investorEmail",email);
//  	  return model;
// 	  
//    }
//    @GetMapping("/updateEmail/{email}")
//    public ModelAndView updateEmailPage(@PathVariable("email")String email) {
// 	   System.out.println("investor email to update password : "+email);
// 	   ModelAndView model = new ModelAndView("updateEmail");
//  	  model.addObject("investorEmail",email);
//  	  return model;
// 	  
//    }
//    @GetMapping("/updatePassword/{email}")
//    public ModelAndView updatePasswordPage(@PathVariable("email")String email) {
// 	   System.out.println("investor email to update : "+email);
// 	   ModelAndView model = new ModelAndView("updatePassword");
//  	  model.addObject("investorEmail",email);
//  	  return model;
// 	  
//    }
//    @PatchMapping("/submitInvestorEmailUpdate")
//    public ResponseEntity<?> investorEmailUpdate(@RequestParam("investorEmail")String investorEmail,@RequestParam("newEmail")String newEmail,@RequestParam("investorPassword")String investorPassword){
// 	   return investorService.emailUpdate(investorEmail,newEmail,investorPassword);
//    }
//    @PatchMapping("/submitInvestorPasswordUpdate")
//    public ResponseEntity<?> investorPasswordUpdate(@RequestParam("investorEmail")String investorEmail,@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword){
// 	   return investorService.passwordUpdate(investorEmail,oldPassword,newPassword);
//    }
//    @PostMapping("/resetPassword")
//    public String resetPassword(@RequestParam String email, Model model) {
//        String newPassword = investorService.resetPassword(email);
//        if (newPassword != null) {
//            model.addAttribute("success", "New password sent to your email.");
//        } else {
//            model.addAttribute("error", "Email not found.");
//        }
//        return "loginInvestor";
//    }
//
//    
//    @GetMapping("/addFunds/{id}")
//    public String showAddFundsPage(@PathVariable Long id, Model model) {
//        model.addAttribute("investorId", id);
//        return "addFunds";
//    }
//    @GetMapping("/withdrawFunds/{id}")
//    public String showWithdrawFundsPage(@PathVariable Long id, Model model) {
//        model.addAttribute("investorId", id);
//        return "withdrawFunds";
//    }
//
//   
//    @PostMapping("/addFunds/{id}")
//    public String addFunds(@PathVariable Long id, @RequestParam BigDecimal amount, Model model) {
//        try {
//            investorService.addFunds(id, amount);
//            
//            return "redirect:/viewBaskets/" + id;
//        } catch (Exception e) {
//            model.addAttribute("error", "Error adding funds: " + e.getMessage());
//            return "wallet";
//        }
//    }
//
//    @PostMapping("/withdrawFunds/{id}")
//    public String withdrawFunds(@PathVariable Long id, @RequestParam BigDecimal amount, Model model) {
//        try {
//            investorService.withdrawFunds(id, amount);
//            model.addAttribute("success", "Funds withdrawn successfully.");
//            return "redirect:/viewBaskets/" + id;
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("error", e.getMessage());
//            return "viewBaskets";
//        }
//        
//    }
//
//    @GetMapping("/viewBaskets/{investorId}")
//    public String viewBaskets(@PathVariable Long investorId, Model model) {
//        List<Basket> baskets = basketService.getAllBaskets();
//        model.addAttribute("baskets", baskets);
//        model.addAttribute("investorId", investorId);
//        return "viewBaskets";
//    }
//
//    @GetMapping("/viewBasketsByAdvisor/{investorId}/{advisorName}")
//    public String viewBasketsByAdvisor(@PathVariable Long investorId, @PathVariable String advisorName, Model model) {
//        String decodedAdvisorName = URLDecoder.decode(advisorName, StandardCharsets.UTF_8);
//        System.out.println("Decoded Advisor Name: " + decodedAdvisorName);
//        List<Basket> baskets = basketService.getBasketsByAdvisor(decodedAdvisorName);
//        model.addAttribute("baskets", baskets);
//        model.addAttribute("investorId", investorId);
//        model.addAttribute("advisorName", decodedAdvisorName);
//        return "viewBasketByAdvisor";
//    }
//
//
//
//    @GetMapping("/viewBasketDetails/{investorId}/{basketId}")
//    public String viewBasketDetails(@PathVariable Long investorId, @PathVariable Long basketId, Model model) {
//        Basket basket = basketService.getBasketById(basketId);
//        model.addAttribute("basket", basket);
//        model.addAttribute("investorId", investorId);
//        return "basketDetails";
//    }
//
//    @PostMapping("/investInBasket/{investorId}/{basketId}")
//    public String investInBasket(@PathVariable Long investorId, @PathVariable Long basketId, @RequestParam int units, Model model) {
//        try {
//            BigDecimal totalPrice = basketService.getBasketById(basketId).getTotalPrice();
//            BigDecimal totalInvestment = totalPrice.multiply(BigDecimal.valueOf(units));
//            investmentService.investInBasket(investorId, basketId, totalInvestment);
//            model.addAttribute("success", "Investment successful.");
//            return "redirect:/viewInvestments/" + investorId;
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("error", e.getMessage());
//            return "redirect:/addFunds/" + investorId;
//        }
//    }
//    @Autowired 
//    private InvestmentRepository investmentRepository;
//    @Autowired 
//    private InvestorRepository investorRepository;
//    @PostMapping("/redeemInvestment/{investmentId}")
//    public String redeemInvestment(@PathVariable Long investmentId, Model model) {
//        Investment investment = investmentRepository.findById(investmentId).orElseThrow(() -> new IllegalArgumentException("Invalid investment ID: " + investmentId));
//        investmentService.redeemInvestment(investmentId);
//        model.addAttribute("success", "Investment redeemed successfully.");
//        return "redirect:/viewInvestments/" + investment.getInvestor().getId();
//    }
//
//
//    @GetMapping("/viewInvestments/{investorId}")
//    public String viewInvestments(@PathVariable Long investorId, Model model) {
//        List<Investment> investments = investmentService.getInvestmentsByInvestor(investorId);
//        model.addAttribute("investments", investments);
//        model.addAttribute("investorId", investorId);
//        return "viewInvestments";
//    }
//   
//    @GetMapping("/viewInvestedBasketDetails/{investorId}/{investmentId}")
//    public String viewInvestedBasketDetails(@PathVariable Long investorId, @PathVariable Long investmentId, Model model) {
//        Investment investment = investmentRepository.findById(investmentId).orElseThrow(() -> new IllegalArgumentException("Invalid investment ID: " + investmentId));
//        Basket basket = investment.getBasket();
//        BigDecimal units = investment.getAmountInvested().divide(basket.getTotalPrice(), RoundingMode.HALF_UP);
//
//        // Fetch stock prices on the investment day and current day
//        Map<Stock, BigDecimal> investmentDayPrices = new HashMap<>();
//        Map<Stock, BigDecimal> currentDayPrices = new HashMap<>();
//        for (Stock stock : basket.getStocks()) {
//        	LocalDate dayOfInvestment =  investment.getInvestmentDate().toLocalDate();
//            BigDecimal investmentDayPrice = stockPriceService.getStockPriceOnDate(stock.getSymbol(), dayOfInvestment.minusDays(10));
//            BigDecimal currentDayPrice = stock.getClosingPrice(); // Assuming closingPrice is the current price
//
//            investmentDayPrices.put(stock, investmentDayPrice);
//            currentDayPrices.put(stock, currentDayPrice);
//        }
//
//        model.addAttribute("investment", investment);
//        model.addAttribute("basket", basket);
//        model.addAttribute("units", units);
//        model.addAttribute("investmentDayPrices", investmentDayPrices);
//        model.addAttribute("currentDayPrices", currentDayPrices);
//        model.addAttribute("investorId", investorId);
//        return "investedBasketDetails";
//    }
//    @GetMapping("/viewPortfolio/{investorId}")
//    public String viewPortfolio(@PathVariable Long investorId, Model model) {
//        Investor investor = investorRepository.findById(investorId).orElseThrow(() -> new IllegalArgumentException("Invalid investor ID: " + investorId));
//        List<Investment> investments = investmentRepository.findByInvestor(investor);
//
//        BigDecimal totalAmountInvested = BigDecimal.ZERO;
//        BigDecimal totalCurrentValue = BigDecimal.ZERO;
//
//        for (Investment investment : investments) {
//            totalAmountInvested = totalAmountInvested.add(investment.getAmountInvested());
//            totalCurrentValue = totalCurrentValue.add(investment.getCurrentValue());
//        }
//
//        model.addAttribute("investor", investor);
//        model.addAttribute("investments", investments);
//        model.addAttribute("totalAmountInvested", totalAmountInvested);
//        model.addAttribute("totalCurrentValue", totalCurrentValue);
//        return "viewPortfolio";
//    }
//
////    @GetMapping("/viewInvestedBasketDetails/{investorId}/{investmentId}")
////    public String viewInvestedBasketDetails(@PathVariable Long investorId, @PathVariable Long investmentId, Model model) {
////        Investment investment = investmentRepository.findById(investmentId).orElseThrow(() -> new IllegalArgumentException("Invalid investment ID: " + investmentId));
////        Basket basket = investment.getBasket();
////        BigDecimal units = investment.getAmountInvested().divide(basket.getTotalPrice(), RoundingMode.HALF_UP);
////        model.addAttribute("investment", investment);
////        model.addAttribute("basket", basket);
////        model.addAttribute("units", units);
////        model.addAttribute("investorId", investorId);
////        return "investedBasketDetails";
////    }
//
//}
