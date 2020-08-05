package br.com.academic.controllers;


import br.com.academic.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.academic.service.AlunoService;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoService as;

	
	@RequestMapping(value = "/aluno/imagem/{id_aluno}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("id_aluno") long id_aluno) {
		Aluno aluno = this.as.getOneAlunoById(id_aluno);
		Usuario usuarioAluno = aluno.getUsuario();
		return usuarioAluno.getImagem();
	}

}
