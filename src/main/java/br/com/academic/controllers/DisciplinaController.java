package br.com.academic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.models.Disciplina;
import br.com.academic.repository.DisciplinaRepository;

@Controller
public class DisciplinaController {
	
	@Autowired
	private DisciplinaRepository dr;
	
	@RequestMapping(value="/disciplinas", method=RequestMethod.GET)
	public ModelAndView listarDisciplina() {
		
		ModelAndView mv = new ModelAndView("disciplinas/listar_disciplina");
		List<Disciplina> disciplinas = dr.findAll();
		mv.addObject("disciplinas", disciplinas);
		
		return mv;
	}

}
