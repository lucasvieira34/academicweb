package br.com.academic.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.models.Aluno;
import br.com.academic.models.AlunoDisciplina;
import br.com.academic.models.Usuario;
import br.com.academic.service.UsuarioService;

@Controller
@RequestMapping("/aluno")
public class AlunoControllerRef {
	
	@Autowired
	private UsuarioService us;
	
	private Usuario usuarioLogado;

	// LISTAR TODAS AS DISCIPLINAS DO ALUNO LOGADO
	@RequestMapping(value = "/disciplinas", method = RequestMethod.GET)
	public ModelAndView minhasDisciplinas() {
		usuarioLogado();

		Aluno aluno = usuarioLogado.getAluno();
		Set<AlunoDisciplina> alunoDisciplina = aluno.getExtratos();

		ModelAndView mv = new ModelAndView("disciplinas/disciplina_aluno");
		mv.addObject("usuarioLogado", usuarioLogado);
		mv.addObject("alunoDisciplina", alunoDisciplina);

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
