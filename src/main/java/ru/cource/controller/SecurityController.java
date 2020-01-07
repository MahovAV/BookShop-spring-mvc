package ru.cource.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.cource.model.domain.User;
import ru.cource.model.service.UserServiceInterface;
import ru.cource.model.validation.UserValidator;

/**
 * 
 * @author AlexanderM-O
 *
 */
@Controller
@RequestMapping("/")
public class SecurityController {
	@Autowired
	UserServiceInterface userService;
	@Autowired
	UserValidator userValidator;
	
    @GetMapping("/")
    public String welcomePage(){
        return "WelcomePage";
    }
    
    @GetMapping(value = "/signUp")
    public String registrationPage(){
        return "Security/RegistrationPage";
    }
    @PostMapping(value = "/signUp")
    public String registering(@Valid @ModelAttribute User user,BindingResult bindingResult,Model model) {
    	userValidator.validate(user, bindingResult);
    	if(bindingResult.hasErrors()) {
    		Map<String,String> FiledErrors=ControllerUtils.getErrors(bindingResult);
    		model.mergeAttributes(FiledErrors);
    		model.addAttribute("user",user);
    		return("Security/RegistrationPage");
    	}
    	userService.registerUser(user);
    	return "redirect:/login";
    }
    
    @GetMapping(value = "/signIn")
    public String loginPage(@RequestParam(name = "error", required = false) Boolean error,
            Model model) {
    	if(Boolean.TRUE.equals(error)) { //not if(error) because error could be null
    		model.addAttribute("loginError","Wrong username or password");
    	}
    	return "Security/LoginPage";
    }       
}
