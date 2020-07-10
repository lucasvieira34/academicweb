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

import br.com.academic.models.Disciplina;
import br.com.academic.models.Professor;
import br.com.academic.models.Usuario;
import br.com.academic.service.DisciplinaService;
import br.com.academic.service.UsuarioService;

@Controller
public class DisciplinaController {

	@Autowired
	private DisciplinaService ds;

	@Autowired
	private UsuarioService us;

	private Usuario usuario;

	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuario = us.usuarioPorLogin(login);
		}
	}

	// LISTAR TODAS AS DISCIPLINAS DO PROFESSOR
	@RequestMapping(value = "/disciplinas", method = RequestMethod.GET)
	public ModelAndView listarDisciplinaProfessor() {
		
		usuarioLogado();
		
		Professor professor = usuario.getProfessor();
		List<Disciplina> disciplinas = professor.getDisciplinas();
		
		ModelAndView mv = new ModelAndView("disciplinas/listar_disciplina");
		mv.addObject("disciplinas", disciplinas);
		mv.addObject("usuario", usuario);

		return mv;
	}

	// LISTAR TODAS AS DISCIPLINAS
	@RequestMapping(value = "/disciplinasTESTE", method = RequestMethod.GET)
	public ModelAndView listarDisciplina() {

		usuarioLogado();
		ModelAndView mv = new ModelAndView("disciplinas/listar_disciplina");
		List<Disciplina> disciplinas = ds.getDisciplinas();
		mv.addObject("disciplinas", disciplinas);
		mv.addObject("usuario", usuario);

		return mv;
	}

}
