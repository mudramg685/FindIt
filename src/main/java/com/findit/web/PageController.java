package com.findit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Friendly extension-free URLs for students typing routes directly. */
@Controller
public class PageController {
    @GetMapping("/login") public String login(){return "forward:/login.html";}
    @GetMapping("/register") public String register(){return "forward:/register.html";}
    @GetMapping("/dashboard") public String dashboard(){return "forward:/dashboard.html";}
    @GetMapping("/report-item") public String reportItem(){return "forward:/report-item.html";}
    @GetMapping("/item-details") public String itemDetails(){return "forward:/item-details.html";}
    @GetMapping("/admin") public String admin(){return "forward:/admin.html";}
}
