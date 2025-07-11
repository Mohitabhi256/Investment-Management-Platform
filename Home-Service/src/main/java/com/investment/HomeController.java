
package com.investment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    // Method to route to the landing page
    @GetMapping("/")
    @ResponseBody
    public ModelAndView showLandingPage() {
        return new ModelAndView("index"); // ModelAndView used instead of returning a string
    }
}

