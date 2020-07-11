package br.com.academic.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.models.Aluno;
import br.com.academic.models.AlunoDisciplina;
import br.com.academic.models.AlunoDisciplinaPK;
import br.com.academic.models.Disciplina;
import br.com.academic.models.Role;
import br.com.academic.models.Usuario;
import br.com.academic.service.AlunoDisciplinaService;
import br.com.academic.service.AlunoService;
import br.com.academic.service.DisciplinaService;
import br.com.academic.service.RoleService;
import br.com.academic.service.UsuarioService;

@Controller
public class AlunoController {

	@Autowired
	private AlunoService as;

	@Autowired
	private DisciplinaService ds;

	@Autowired
	private UsuarioService us;
	
	@Autowired
	private RoleService rs;

	@Autowired
	private AlunoDisciplinaService ads;

	private Usuario usuario;

	// TEMPLATE CADASTRO DE ALUNO
	@RequestMapping(value = "/cadastrarAluno", method = RequestMethod.GET)
	public ModelAndView novoAluno() {

		usuarioLogado();
		ModelAndView mv = new ModelAndView("alunos/novo_aluno");
		mv.addObject("usuario", usuario);
		return mv;
	}

	// SALVAR ALUNO
	@RequestMapping(value = "/cadastrarAluno", method = RequestMethod.POST)
	public String salvarAluno(Aluno aluno, Usuario usuario, AlunoDisciplina alunoDisciplina) {
		usuarioLogado();
		
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

		usuarioLogado();
		ModelAndView mv = new ModelAndView("alunos/listar_alunos");
		List<Aluno> alunos = as.getAlunos();
		mv.addObject("alunos", alunos);
		mv.addObject("usuario", usuario);
		return mv;
	}

	// LISTAR ALUNOS PERTENCENTES À DISCIPLINA
	@RequestMapping(value = "/disciplinas/{id_disciplina}/alunos", method = RequestMethod.GET)
	public ModelAndView listarDisciplinaAluno(@PathVariable("id_disciplina") long id_disciplina) {

		usuarioLogado();
		Disciplina disciplina = ds.getDisciplinaById(id_disciplina);

		Set<AlunoDisciplina> alunoDisciplina = disciplina.getExtratos();

		ModelAndView mv = new ModelAndView("alunos/listar_alunos");
		mv.addObject("alunoDisciplina", alunoDisciplina);
		mv.addObject("disciplina", disciplina);
		mv.addObject("usuario", usuario);

		return mv;
	}

	// EDITAR ALUNO
	@RequestMapping(value = "/disciplinas/{id_disciplina}/editarAluno/{id_aluno}", method = RequestMethod.GET)
	public ModelAndView editarAluno(AlunoDisciplina alunoDisciplina, @PathVariable("id_aluno") long id_aluno, @PathVariable("id_disciplina") long id_disciplina) {
		
		Disciplina disciplina = ds.getDisciplinaById(id_disciplina);
		AlunoDisciplinaPK alunoDisciplinaPK = new AlunoDisciplinaPK(id_aluno,id_disciplina);
		alunoDisciplina.setId(alunoDisciplinaPK);
		alunoDisciplina = ads.getAlunoDisciplinasPorId(alunoDisciplina.getId());
		
		ModelAndView mv = new ModelAndView("alunos/update_aluno");
		mv.addObject("disciplina", disciplina);
		mv.addObject("alunoDisciplina", alunoDisciplina);

		return mv;
	}
	
	// INSERIR NOTAS E FALTAS DO ALUNO
	@RequestMapping(value = "/disciplinas/{id_disciplina}/editarAluno/{id_aluno}/alterar", method = RequestMethod.POST)
	public String alterarAluno(AlunoDisciplina alunoDisciplina, @PathVariable("id_aluno") long id_aluno, @PathVariable("id_disciplina") long id_disciplina) {
		
		Aluno aluno = as.getAlunoById(id_aluno);
		Disciplina disciplina = ds.getDisciplinaById(id_disciplina);
		AlunoDisciplinaPK alunoDisciplinaPK = new AlunoDisciplinaPK(id_aluno, id_disciplina);
		alunoDisciplina.setId(alunoDisciplinaPK);
		alunoDisciplina.setAluno(aluno);
		alunoDisciplina.setDisciplina(disciplina);
		
		ads.salvarAlunoDisciplina(alunoDisciplina);

		return "redirect:/disciplinas/{id_disciplina}/alunos";
	}

	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuario = us.usuarioPorLogin(login);
		}
	}

}
