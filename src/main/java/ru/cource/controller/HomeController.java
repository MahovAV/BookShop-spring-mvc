package ru.cource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.cource.model.domain.User;
import ru.cource.model.service.BookShopServiceInterface;
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    BookShopServiceInterface bookShopService;
    
    @GetMapping("/")
    public String welocome(@AuthenticationPrincipal User user,Model model){
    	model.addAttribute("user", user);
        return "redirect:/getAllBook";
    }
    
    @GetMapping("/Home")
    public String home(@AuthenticationPrincipal User user,Model model){
    	model.addAttribute("user", user);
        return "HomePage";
    }
}
