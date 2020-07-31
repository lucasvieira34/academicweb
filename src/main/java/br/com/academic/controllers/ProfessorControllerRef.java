package br.com.academic.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import br.com.academic.models.Mail;
import br.com.academic.models.Professor;
import br.com.academic.models.Usuario;
import br.com.academic.service.AlunoDisciplinaService;
import br.com.academic.service.AlunoService;
import br.com.academic.service.DisciplinaService;
import br.com.academic.service.EmailService;
import br.com.academic.service.UsuarioService;

@Controller
@RequestMapping("/professor")
public class ProfessorControllerRef {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UsuarioService us;
	
	@Autowired
	private AlunoService as;

	@Autowired
	private DisciplinaService ds;

	@Autowired
	private AlunoDisciplinaService ads;

	private Usuario usuarioLogado;

	// LISTAR TODAS AS DISCIPLINAS DO PROFESSOR
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView minhasDisciplinas() {
		usuarioLogado();

		Professor professor = usuarioLogado.getProfessor();
		List<Disciplina> disciplinasProf = professor.getDisciplinas();

		ModelAndView mv = new ModelAndView("professor/professor-dashboard");
		mv.addObject("disciplinasProf", disciplinasProf);
		mv.addObject("usuarioLogado", usuarioLogado);

		return mv;
	}

	// LISTAR ALUNOS PERTENCENTES À DISCIPLINA
	@RequestMapping(value = "/{nome}/alunos", method = RequestMethod.GET)
	public ModelAndView listarDisciplinaAluno(@PathVariable("nome") String nome) {
		usuarioLogado();

		// BUSCANDO ALUNOS DA DISCIPLINA
		Disciplina disciplina = ds.getDisciplinaByNome(nome);
		Set<AlunoDisciplina> alunoDisciplina = disciplina.getExtratos();

		// BUSCANDO DISCIPLINAS DO PROFESSOR
		Professor professor = usuarioLogado.getProfessor();
		List<Disciplina> disciplinasProf = professor.getDisciplinas();

		ModelAndView mv = new ModelAndView("professor/alunos-disciplina");
		mv.addObject("alunoDisciplina", alunoDisciplina);
		mv.addObject("disciplina", disciplina);
		mv.addObject("disciplinasProf", disciplinasProf);
		mv.addObject("usuarioLogado", usuarioLogado);

		return mv;
	}

	// EDITAR ALUNO
	@RequestMapping(value = "/{nome}/notas/{id_aluno}", method = RequestMethod.GET)
	public ModelAndView editarAluno(AlunoDisciplina alunoDisciplina, @PathVariable("id_aluno") long id_aluno,
			@PathVariable("nome") String nome) {
		usuarioLogado();

		// BUSCANDO NOTAS E FALTAS DOS ALUNOS REFERENTE A DISCIPLINA SELECIONADA
		Disciplina disciplina = ds.getDisciplinaByNome(nome);
		AlunoDisciplinaPK alunoDisciplinaPK = new AlunoDisciplinaPK(id_aluno, disciplina.getId_disciplina());
		alunoDisciplina.setId(alunoDisciplinaPK);
		alunoDisciplina = ads.getAlunoDisciplinasPorId(alunoDisciplina.getId());

		// BUSCANDO DISCIPLINAS DO PROFESSOR
		Professor professor = usuarioLogado.getProfessor();
		List<Disciplina> disciplinasProf = professor.getDisciplinas();

		ModelAndView mv = new ModelAndView("professor/inserir-notas");
		mv.addObject("disciplina", disciplina);
		mv.addObject("alunoDisciplina", alunoDisciplina);
		mv.addObject("disciplinasProf", disciplinasProf);
		mv.addObject("usuarioLogado", usuarioLogado);

		return mv;
	}

	// INSERIR NOTAS E FALTAS DO ALUNO
	@RequestMapping(value = "/{nome}/notas/{id_aluno}/alterar", method = RequestMethod.POST)
	public String alterarAluno(AlunoDisciplina alunoDisciplina, 
							   @PathVariable("id_aluno") long id_aluno,
							   @PathVariable("nome") String nome) throws Exception {

		Aluno aluno = as.getAlunoById(id_aluno);
		Disciplina disciplina = ds.getDisciplinaByNome(nome);
		AlunoDisciplinaPK alunoDisciplinaPK = new AlunoDisciplinaPK(id_aluno, disciplina.getId_disciplina());
		alunoDisciplina.setId(alunoDisciplinaPK);
		alunoDisciplina.setAluno(aluno);
		alunoDisciplina.setDisciplina(disciplina);

		ads.salvarAlunoDisciplina(alunoDisciplina);

		// MÉTODO ENVIAR EMAIL
		Mail mail = new Mail();
		mail.setFrom("academicwebboot@gmail.com");
		mail.setTo(aluno.getUsuario().getEmail());
		mail.setSubject("Sua nota acaba de ser alterada no sistema!");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", aluno.getNome());
		model.put("disciplina", alunoDisciplina.getDisciplina().getNome());
		mail.setModel(model);
		emailService.emailNotas(mail);

		return "redirect:/professor/{nome}/notas/{id_aluno}?success";
	}
	
	
	
	
	

	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuarioLogado = us.usuarioPorLogin(login);
		}
	}

}
