package com.investment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class AdminController {

    @GetMapping("/admindashboard")
    @ResponseBody
    public ModelAndView adminDashboardPage() {
        return new ModelAndView("adminDashboard");
    }

    @Autowired
    private AdvisorService advisorService;

    @GetMapping("/addAdvisor")
    @ResponseBody
    public ModelAndView showAddAdvisorForm() {
        ModelAndView modelAndView = new ModelAndView("addAdvisor");
        modelAndView.addObject("advisor", new Advisor());
        return modelAndView;
    }

    @PostMapping("/addAdvisor")
    @ResponseBody
    public ModelAndView addAdvisor(@ModelAttribute Advisor advisor) {
        advisorService.addAdvisor(advisor);
        return new ModelAndView("redirect:/viewAdvisors");
    }

    @GetMapping("/removeAdvisor")
    @ResponseBody
    public ModelAndView showRemoveAdvisorForm() {
        ModelAndView modelAndView = new ModelAndView("removeAdvisor");
        modelAndView.addObject("advisors", advisorService.viewAdvisors());
        return modelAndView;
    }

    @PostMapping("/removeAdvisor")
    @ResponseBody
    public ModelAndView removeAdvisor(@RequestParam Long advisorId) {
        advisorService.removeAdvisor(advisorId);
        return new ModelAndView("redirect:/viewAdvisors");
    }

    @GetMapping("/viewAdvisors")
    @ResponseBody
    public ModelAndView viewAdvisors() {
        ModelAndView modelAndView = new ModelAndView("viewAdvisors");
        modelAndView.addObject("advisors", advisorService.viewAdvisors());
        return modelAndView;
    }
}
