package br.com.academic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.academic.models.Aluno;
import br.com.academic.models.Professor;
import br.com.academic.models.Usuario;
import br.com.academic.service.AlunoService;
import br.com.academic.service.ProfessorService;

@Controller
@RequestMapping("/imagem")
public class ImagemController {
	
	@Autowired
	private AlunoService as;
	
	@Autowired
	private ProfessorService ps;

	@RequestMapping(value = "/aluno/{id_aluno}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] exibirImagemAluno(@PathVariable("id_aluno") long id_aluno) {
		Aluno aluno = this.as.getOneAlunoById(id_aluno);
		Usuario usuarioAluno = aluno.getUsuario();
		return usuarioAluno.getImagem();
	}
	
	@RequestMapping(value = "/professor/{id_professor}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] exibirImagemProfessor(@PathVariable("id_professor") long id_professor) {
		Professor professor = this.ps.getProfessorById(id_professor);
		Usuario usuarioProfessor = professor.getUsuario();
		return usuarioProfessor.getImagem();
	}

}
