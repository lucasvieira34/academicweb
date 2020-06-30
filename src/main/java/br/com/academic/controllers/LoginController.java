package br.com.academic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	//PÁGINA DE LOGIN
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login/login";
	}
	
	/* @RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView getLogin(Usuario usuario) {
		
		ModelAndView mv = new ModelAndView();
		
		usuario = ur.findByUsuario(usuario);
		
		if(usuario == null) {
			mv.setViewName("login");
		}else {
			mv.setViewName("home");
		} 
		
		//VERIFICAÇÃO DE PERFIS PARA REDIRECIONAMENTO DE PÁGINAS
		
		
		return mv;
	} */
	
}
