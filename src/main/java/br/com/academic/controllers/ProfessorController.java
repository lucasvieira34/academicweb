package br.com.academic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.models.Disciplina;
import br.com.academic.models.Professor;
import br.com.academic.models.Role;
import br.com.academic.models.Usuario;
import br.com.academic.service.DisciplinaService;
import br.com.academic.service.ProfessorService;
import br.com.academic.service.RoleService;
import br.com.academic.service.UsuarioService;

@Controller
public class ProfessorController {

	@Autowired
	private ProfessorService ps;

	@Autowired
	private DisciplinaService ds;
	
	@Autowired
	private RoleService rs;

	@Autowired
	private UsuarioService us;
	
	private Usuario usuario;

	// TEMPLATE CADASTRO DE PROFESSOR
	@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.GET)
	public ModelAndView novoProfessor() {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("professor/novo_professor");
		mv.addObject("usuario", usuario);
		return mv;
	}

	// SALVAR PROFESSOR
	@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.POST)
	public String salvarProfessor(Professor professor, Usuario usuario) {
		usuarioLogado();
		
		professor.setUsuario(usuario);
		ps.salvarProfessor(professor);
				
		List<Role> roles = rs.getRoles();
		
		//SETANDO TODAS AS ROLES
		usuario.setRoles(roles);
		//REMOVENDO A ROLE DE ALUNO DA LISTA
		usuario.getRoles().remove(0);
		
		us.salvarUsuario(usuario);
		
		return "redirect:/professores";
	}

	// LISTAR PROFESSORES
	@RequestMapping(value = "/professores", method = RequestMethod.GET)
	public ModelAndView listarProfessores() {

		usuarioLogado();
		ModelAndView mv = new ModelAndView("professor/listar_professores");
		mv.addObject("professores", ps.getProfessores());
		mv.addObject("usuario", usuario);
		return mv;
	}

	// EDITAR PROFESSOR
	@RequestMapping(value = "/editarProfessor/{id_professor}", method = RequestMethod.GET)
	public ModelAndView editarProfessor(@PathVariable("id_professor") long id) {

		Professor professor = ps.getProfessorById(id);

		ModelAndView mv = new ModelAndView("professor/associar_disciplina");
		mv.addObject("professor", professor);
		
		List<Disciplina> disciplinasNaoAssociadas = ds.getDisciplinas();
		disciplinasNaoAssociadas.removeAll(professor.getDisciplinas());
		mv.addObject("disciplinas", disciplinasNaoAssociadas);

		return mv;
	}
	
	// ASSOCIAR PROFESSOR DISCIPLINA
	@RequestMapping(value = "/associarDisciplina", method = RequestMethod.POST)
	public String associarDisciplina(@ModelAttribute Disciplina disciplina, @RequestParam Long id_professor) {
		
		Professor professor = ps.getProfessorById(id_professor);
		disciplina = ds.getDisciplinaById(disciplina.getId_disciplina());
		
		professor.getDisciplinas().add(disciplina);
		ps.salvarProfessor(professor);
		
		return "redirect:/editarProfessor/" + id_professor;
	}
	
	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if(!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuario = us.usuarioPorLogin(login);
		}
	}

}
