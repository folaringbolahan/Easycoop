package com.sift.security.logon.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LogonController {
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String executeSecurity(ModelMap model, Principal principal ) { 
		String name = principal.getName();
		model.addAttribute("author", name);
		model.addAttribute("message", "Welcome To Login Form Based Spring Security Example!!!");
		return "welcome"; 
	}
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) { 
		return "login"; 
	}
 
	@RequestMapping(value="/fail2login", method = RequestMethod.GET)
	public String loginerror(ModelMap model) { 
		model.addAttribute("error", "true");
		return "login"; 
	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) { 
		return "login"; 
	}
        
         @RequestMapping(value="/votes/login", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("votes/login");
		return modelAndView;
	}
        
        
        /**
        @RequestMapping(value="/vlogout", method = RequestMethod.GET)
	public String vlogout(ModelMap model) { 
		return "vlogin"; 
	}
        @RequestMapping(value="/vlogin", method = RequestMethod.GET)
	public String vlogin(ModelMap model) { 
            System.out.println("vlogin called");
		return "vlogin"; 
	}
        
       
        
        @RequestMapping(value={"votes/postlogin","/postlogin"},method = {RequestMethod.GET,RequestMethod.POST})
	public String doLogin(){
		return "j_spring_security_check";
	}***/
}