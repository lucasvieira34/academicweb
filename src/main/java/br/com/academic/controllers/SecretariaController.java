package br.com.academic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.models.Aluno;
import br.com.academic.models.Usuario;
import br.com.academic.service.AlunoService;
import br.com.academic.service.UsuarioService;

@Controller
@RequestMapping("/secretaria")
public class SecretariaController {

	@Autowired
	private UsuarioService us;
	
	@Autowired
	private AlunoService as;

	private Usuario usuarioLogado;

	// DASHBOARD DE SECRETARIA
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("secretaria/secretaria-dashboard");
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// LISTAR TODOS OS ALUNOS
	@RequestMapping(value = "/alunos", method = RequestMethod.GET)
	public ModelAndView listarTodosAlunos() {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("secretaria/secretaria-alunos");
		List<Aluno> alunos = as.getAlunos();
		mv.addObject("alunos", alunos);
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}
	
	
	
	
	
	

	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuarioLogado = us.usuarioPorLogin(login);
		}
	}

}
