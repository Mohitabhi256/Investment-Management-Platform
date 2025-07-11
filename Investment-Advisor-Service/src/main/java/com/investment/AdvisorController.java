package com.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AdvisorController {

    @Autowired
    private StockService stockService;

    @Autowired
    private AdvisorRepository advisorRepository;
    @GetMapping("/advisordashboard/{id}")
    @ResponseBody
    public ModelAndView advisorDashboardPage(@PathVariable Long id) {
    	Advisor advisor = advisorRepository.findByAdvisorId(id);
        ModelAndView modelAndView = new ModelAndView("advisorDashboard");
        modelAndView.addObject("advisorId", id);
        modelAndView.addObject("email", advisor.getEmail());
        return modelAndView;
    }
    @GetMapping("/viewAdvisorProfile/{id}")
    @ResponseBody
    public ModelAndView viewProfilePage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("viewAdvisorProfile");
        modelAndView.addObject("advisorId", id);
        return modelAndView;
    }


    @GetMapping("/updateAdvisorProfile/{email}")
    @ResponseBody
    public ModelAndView updateProfilePage(@PathVariable("email") String email) {
        System.out.println("advisor email to update profile: " + email);
        ModelAndView model = new ModelAndView("updateAdvisorProfile");
        model.addObject("advisorEmail", email);
        return model;
    }

    @GetMapping("/updateAdvisorEmail/{email}")
    @ResponseBody
    public ModelAndView updateEmailPage(@PathVariable("email") String email) {
        System.out.println("advisor email to update email: " + email);
        ModelAndView model = new ModelAndView("updateAdvisorEmail");
        model.addObject("advisorEmail", email);
        return model;
    }

    @GetMapping("/updateAdvisorPassword/{email}")
    @ResponseBody
    public ModelAndView updatePasswordPage(@PathVariable("email") String email) {
        System.out.println("Advisor email to update password: " + email);
        ModelAndView model = new ModelAndView("updateAdvisorPassword");
        model.addObject("advisorEmail", email);
        return model;
    }

    @PatchMapping("/submitAdvisorEmailUpdate")
    public ResponseEntity<?> advisorEmailUpdate(@RequestParam("advisorEmail") String advisorEmail,
                                                 @RequestParam("newEmail") String newEmail,
                                                 @RequestParam("advisorPassword") String advisorPassword) {
        return advisorService.emailUpdate(advisorEmail, newEmail, advisorPassword);
    }

    @PatchMapping("/submitAdvisorPasswordUpdate")
    public ResponseEntity<?> advisorPasswordUpdate(@RequestParam("advisorEmail") String advisorEmail,
                                                    @RequestParam("oldPassword") String oldPassword,
                                                    @RequestParam("newPassword") String newPassword) {
        return advisorService.passwordUpdate(advisorEmail, oldPassword, newPassword);
    }

    @GetMapping("/viewStocks/{id}")
    @ResponseBody
    public ModelAndView viewStocksPage(@PathVariable Long id) {
        List<Stock> stocks = stockService.getAllStocks();
        ModelAndView modelAndView = new ModelAndView("viewStocks");
        modelAndView.addObject("stocks", stocks);
        modelAndView.addObject("advisorId", id);
        return modelAndView;
    }

    @GetMapping("/viewStock/{id}/{symbol}")
    @ResponseBody
    public ModelAndView viewStockDetailsPage(@PathVariable Long id, @PathVariable String symbol) {
        Stock stock = stockService.getStockBySymbol(symbol);
        List<StockPrice> stockPrices = stockService.getStockPricesBySymbol(symbol);
        ModelAndView modelAndView = new ModelAndView("viewStockDetails");
        modelAndView.addObject("stock", stock);
        modelAndView.addObject("stockPrices", stockPrices);
        modelAndView.addObject("advisorId", id);
        return modelAndView;
    }

    @GetMapping("/createBasket/{id}")
    @ResponseBody
    public ModelAndView createBasket(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("createBasket");
        modelAndView.addObject("basket", new Basket());
        modelAndView.addObject("advisorId", id);
        return modelAndView;
    }

    @GetMapping("/selectStocks/{id}")
    @ResponseBody
    public ModelAndView selectStocks(@PathVariable Long id) {
        List<Stock> stocks = stockService.getAllStocks();
        ModelAndView modelAndView = new ModelAndView("selectStocks");
        modelAndView.addObject("stocks", stocks);
        modelAndView.addObject("advisorId", id);
        return modelAndView;
    }

    @Autowired
    private AdvisorService advisorService;

    @Autowired
    private BasketService basketService;

    @PostMapping("/createBasket/{id}")
    @ResponseBody
    public ModelAndView submitBasket(@PathVariable Long id, @RequestParam String basketName, 
                                     @RequestParam String strategy, HttpSession session) {
        Map<Long, Integer> stockUnits = (Map<Long, Integer>) session.getAttribute("basketStocks");
        if (stockUnits == null) {
            stockUnits = new HashMap<>();
        }
        String createdBy = advisorService.getAdvisorNameById(id);
        basketService.createBasket(basketName, stockUnits, strategy, createdBy);
        session.removeAttribute("basketStocks");
        return new ModelAndView("redirect:/viewMyBaskets/" + id);
    }

    @GetMapping("/viewMyBaskets/{id}")
    @ResponseBody
    public ModelAndView viewMyBaskets(@PathVariable Long id) {
        String advisorName = advisorService.getAdvisorNameById(id);
        List<Basket> baskets = basketService.getBasketsByAdvisorName(advisorName);
        ModelAndView modelAndView = new ModelAndView("viewMyBaskets");
        modelAndView.addObject("baskets", baskets);
        modelAndView.addObject("advisorId", id);
        return modelAndView;
    }

    @GetMapping("/basketDetails/{id}/{basketId}")
    @ResponseBody
    public ModelAndView basketDetails(@PathVariable Long id, @PathVariable Long basketId) {
        Basket basket = basketService.getBasketById(basketId);
        ModelAndView modelAndView = new ModelAndView("basketDetails");
        modelAndView.addObject("basket", basket);
        modelAndView.addObject("advisorId", id);
        return modelAndView;
    }

    @PostMapping("/addToBasket/{advisorId}")
    @ResponseBody
    public ResponseEntity<String> addToBasket(@PathVariable Long advisorId, @RequestParam Long stockId, @RequestParam int units, HttpSession session) {
        Map<Long, Integer> basketStocks = (Map<Long, Integer>) session.getAttribute("basketStocks");
        if (basketStocks == null) {
            basketStocks = new HashMap<>();
        }
        basketStocks.put(stockId, units);
        session.setAttribute("basketStocks", basketStocks);
        return ResponseEntity.ok("Stock added to basket");
    }

    @PostMapping("/removeFromBasket/{id}")
    @ResponseBody
    public ModelAndView removeFromBasket(@PathVariable Long id, @RequestParam Long stockId, HttpSession session) {
        Map<Long, Integer> basketStocks = (Map<Long, Integer>) session.getAttribute("basketStocks");
        if (basketStocks != null) {
            basketStocks.remove(stockId);
        }
        session.setAttribute("basketStocks", basketStocks);
        return new ModelAndView("redirect:/viewMyBaskets/" + id);
    }

    @GetMapping("/getSelectedStocks/{id}")
    @ResponseBody
    public List<Map<String, Object>> getSelectedStocks(@PathVariable Long id, HttpSession session) {
        Map<Long, Integer> basketStocks = (Map<Long, Integer>) session.getAttribute("basketStocks");
        if (basketStocks == null) {
            basketStocks = new HashMap<>();
        }
        List<Stock> stocks = stockService.getAllStocksByIds(basketStocks.keySet());
        List<Map<String, Object>> result = new ArrayList<>();
        for (Stock stock : stocks) {
            Map<String, Object> stockData = new HashMap<>();
            stockData.put("id", stock.getId());
            stockData.put("symbol", stock.getSymbol());
            stockData.put("closingPrice", stock.getClosingPrice());
            stockData.put("units", basketStocks.get(stock.getId()));
            result.add(stockData);
        }
        return result;
    }
}

//    @GetMapping("/advisordashboard/{id}")
//    public String advisorDashboardPage(@PathVariable Long id, Model model) {
//        // You can add any additional logic here if needed
//        model.addAttribute("advisorId", id);
//        return "advisorDashboard"; // returns the advisor's dashboard page
//    }
//
//
//    @GetMapping("/viewProfile")
//    public String viewProfilePage() {
//        return "viewProfile"; // returns the viewProfile page
//    }
//
//    @GetMapping("/updateProfile")
//    public String updateProfilePage() {
//        return "updateProfile"; // returns the updateProfile page
//    }
//
//    @GetMapping("/viewStocks")
//    public String viewStocksPage(Model model) {
//        List<Stock> stocks = stockService.getAllStocks();
//        model.addAttribute("stocks", stocks);
//        return "viewStocks"; // returns the viewStocks page
//    }
//
//    @GetMapping("/viewStock/{symbol}")
//    public String viewStockDetailsPage(@PathVariable String symbol, Model model) {
//    	Stock stock = stockService.getStockBySymbol(symbol);
//        List<StockPrice> stockPrices = stockService.getStockPricesBySymbol(symbol);
//        
//        model.addAttribute("stock", stock);
//        model.addAttribute("stockPrices", stockPrices);
//        
//        return "viewStockDetails";
//    }
//
//    @GetMapping("/createBasket")
//    public String createBasket(Model model) {
//        model.addAttribute("basket", new Basket());
//        return "createBasket";
//    }
//    
//    @Autowired
//    private BasketService basketService;
//    
//    @GetMapping("/selectStocks")
//    public String selectStocks(Model model) {
//        List<Stock> stocks = stockService.getAllStocks();
//        model.addAttribute("stocks", stocks);
//        return "selectStocks";
//    }
//	@Autowired
//	private AdvisorService advisorService;
//	@PostMapping("/createBasket")
//	public String submitBasket(@RequestParam String basketName, 
//	                           @RequestParam String strategy, 
//	                           HttpSession session) {
//	    List<Long> stockIds = (List<Long>) session.getAttribute("basketStocks");
//	    if (stockIds == null) {
//	        stockIds = new ArrayList<>();
//	    }
//	    String email = (String) session.getAttribute("email");
//	    System.out.println(email);
//	    String createdBy = advisorService.getAdvisorNameByEmail(email); // Fetch advisor name using email
//	    
//	    basketService.createBasket(basketName, stockIds, strategy, createdBy);
//	    session.removeAttribute("basketStocks");
//	    return "viewMyBaskets";
//	}
//
//	@GetMapping("/viewMyBaskets")
//	public String viewMyBaskets(Model model) {
//	    // Assuming you have a BasketService to fetch the baskets
//	    List<Basket> baskets = basketService.getAllBaskets();
//	    model.addAttribute("baskets", baskets);
//	    return "viewMyBaskets";
//	}
//
//    @GetMapping("/basketDetails/{id}")
//    public String basketDetails(@PathVariable Long id, Model model) {
//        Basket basket = basketService.getBasketById(id);
//        model.addAttribute("basket", basket);
//        return "basketDetails";
//    }
//    @PostMapping("/addToBasket")
//    @ResponseBody
//    public String addToBasket(@RequestParam Long stockId, HttpSession session) {
//        List<Long> basketStocks = (List<Long>) session.getAttribute("basketStocks");
//        if (basketStocks == null) {
//            basketStocks = new ArrayList<>();
//        }
//        if (!basketStocks.contains(stockId)) {
//            basketStocks.add(stockId);
//        }
//        session.setAttribute("basketStocks", basketStocks);
//        return "Stock added to basket";
//    }
//
//    @PostMapping("/removeFromBasket")
//    @ResponseBody
//    public String removeFromBasket(@RequestParam Long stockId, HttpSession session) {
//        List<Long> basketStocks = (List<Long>) session.getAttribute("basketStocks");
//        if (basketStocks != null) {
//            basketStocks.remove(stockId);
//        }
//        session.setAttribute("basketStocks", basketStocks);
//        return "Stock removed from basket";
//    }
//    @GetMapping("/getSelectedStocks")
//    @ResponseBody
//    public List<Stock> getSelectedStocks(HttpSession session) {
//        List<Long> basketStocks = (List<Long>) session.getAttribute("basketStocks");
//        if (basketStocks == null) {
//            return new ArrayList<>();
//        }
//        return stockService.getStocksByIds(basketStocks);
//    }
//}

