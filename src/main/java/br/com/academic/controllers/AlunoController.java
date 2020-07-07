package br.com.academic.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.models.Aluno;
import br.com.academic.models.AlunoDisciplina;
import br.com.academic.models.AlunoDisciplinaPK;
import br.com.academic.models.Disciplina;
import br.com.academic.models.Usuario;
import br.com.academic.service.AlunoDisciplinaService;
import br.com.academic.service.AlunoService;
import br.com.academic.service.UsuarioService;

@Controller
public class AlunoController {

	@Autowired
	private AlunoService as;

	@Autowired
	private UsuarioService us;
	
	@Autowired
	private AlunoDisciplinaService ads;

	// TEMPLATE CADASTRO DE ALUNO
	@RequestMapping(value = "/cadastrarAluno", method = RequestMethod.GET)
	public String novoAluno() {
		return "alunos/novo_aluno";
	}

	// SALVAR ALUNO
	@RequestMapping(value = "/cadastrarAluno", method = RequestMethod.POST)
	public String salvarAluno(Aluno aluno, Usuario usuario, AlunoDisciplina alunoDisciplina) {

		us.salvarUsuario(usuario);
		aluno.setUsuario(usuario);
		as.salvarAluno(aluno);
		
		for (int i = 1; i < 13; i++) {
			Disciplina disciplina = new Disciplina();
			disciplina.setId_disciplina(i);
			
			alunoDisciplina.setAluno(aluno);
			alunoDisciplina.setDisciplina(disciplina);
						
			AlunoDisciplinaPK alunoDisciplinaPk = new AlunoDisciplinaPK(aluno.getId_aluno(),disciplina.getId_disciplina());
			alunoDisciplina.setId(alunoDisciplinaPk);
			
			ads.salvarAlunoDisciplina(alunoDisciplina);
		}

		return "redirect:/alunos";
	}

	// LISTAR ALUNOS
	@RequestMapping(value = "/alunos", method = RequestMethod.GET)
	public ModelAndView listarAlunos() {

		ModelAndView mv = new ModelAndView("alunos/listar_alunos");
		List<Aluno> alunos = as.getAlunos();
		mv.addObject("alunos", alunos);
		// mv.addObject("alunos", as.getAlunos());
		return mv;
	}

	// EDITAR ALUNO
	@RequestMapping(value = "/editarAluno/{id_aluno}", method = RequestMethod.GET)
	public ModelAndView editarAluno(@PathVariable("id_aluno") long id) {

		Aluno aluno = as.getAlunoById(id);

		ModelAndView mv = new ModelAndView("alunos/update_aluno");
		mv.addObject("aluno", aluno);

		return mv;
	}

}
