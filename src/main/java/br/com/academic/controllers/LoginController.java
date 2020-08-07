package br.com.academic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	//HOMEPAGE
	@RequestMapping("/")
	public String index() {
		return "home/index";
	}
	
	//VIEW DE LOGIN
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		ModelAndView mv = new ModelAndView("login/login");
		return mv;
	}
	
}
