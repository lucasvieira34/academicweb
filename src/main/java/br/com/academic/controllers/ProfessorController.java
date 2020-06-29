package br.com.academic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.models.Disciplina;
import br.com.academic.models.Professor;
import br.com.academic.models.Usuario;
import br.com.academic.service.DisciplinaService;
import br.com.academic.service.ProfessorService;
import br.com.academic.service.UsuarioService;

@Controller
public class ProfessorController {

	@Autowired
	private ProfessorService ps;

	@Autowired
	private DisciplinaService ds;

	@Autowired
	private UsuarioService us;

	// TEMPLATE CADASTRO DE PROFESSOR
	@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.GET)
	public ModelAndView novoProfessor() {
		ModelAndView mv = new ModelAndView("professor/novo_professor");
		return mv;
	}

	// SALVAR PROFESSOR
	@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.POST)
	public String salvarProfessor(Professor professor, Usuario usuario) {

		us.salvarUsuario(usuario);
		professor.setUsuario(usuario);
		ps.salvarProfessor(professor);

		return "redirect:/professores";
	}

	// LISTAR PROFESSORES
	@RequestMapping(value = "/professores", method = RequestMethod.GET)
	public ModelAndView listarProfessores() {

		ModelAndView mv = new ModelAndView("professor/listar_professores");
		mv.addObject("professores", ps.getProfessores());
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
	
	

}
