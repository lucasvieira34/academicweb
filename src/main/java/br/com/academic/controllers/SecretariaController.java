package br.com.academic.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.dto.CadastroProfessorDto;
import br.com.academic.models.Aluno;
import br.com.academic.models.Disciplina;
import br.com.academic.models.Professor;
import br.com.academic.models.Role;
import br.com.academic.models.Usuario;
import br.com.academic.service.AlunoService;
import br.com.academic.service.CadastroProfessorService;
import br.com.academic.service.DisciplinaService;
import br.com.academic.service.ProfessorService;
import br.com.academic.service.RoleService;
import br.com.academic.service.UsuarioService;

@Controller
@RequestMapping("/secretaria")
public class SecretariaController {

	@Autowired
	private UsuarioService us;

	@Autowired
	private AlunoService as;

	@Autowired
	private ProfessorService ps;

	@Autowired
	private DisciplinaService ds;
	
	@Autowired
	private RoleService rs;
	
	@Autowired
	private CadastroProfessorService cps;

	private Usuario usuarioLogado;
	
	@ModelAttribute("dto")
	public CadastroProfessorDto cadastroProfessorDto() {
		return new CadastroProfessorDto();
	}

	// DASHBOARD DE SECRETARIA
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("secretaria/secretaria-dashboard");
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// LISTAR TODOS OS ALUNOS
	@RequestMapping(value = "/alunos", method = RequestMethod.GET)
	public ModelAndView listarTodosAlunos() {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("secretaria/secretaria-alunos");
		List<Aluno> alunos = as.getAlunos();
		mv.addObject("alunos", alunos);
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// LISTAR TODOS OS PROFESSORES
	@RequestMapping(value = "/professores", method = RequestMethod.GET)
	public ModelAndView listarTodosProfessores() {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("secretaria/secretaria-professores");
		List<Professor> professores = ps.getProfessores();
		mv.addObject("professores", professores);
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// LISTAR TODAS AS DISCIPLINAS
	@RequestMapping(value = "/disciplinas", method = RequestMethod.GET)
	public ModelAndView listarTodasDisciplinas() {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("secretaria/secretaria-disciplinas");
		List<Disciplina> disciplinas = ds.getDisciplinas();
		mv.addObject("disciplinas", disciplinas);
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// FORM DE ASSOCIAÇÃO DE DISCIPLINAS
	@RequestMapping(value = "/professor/disciplinas/{id_professor}", method = RequestMethod.GET)
	public ModelAndView formAssociacaoDisciplinas(@PathVariable("id_professor") long id) {
		usuarioLogado();
		Professor professor = ps.getProfessorById(id);
		ModelAndView mv = new ModelAndView("secretaria/associar-disciplina");
		mv.addObject("professor", professor);
		mv.addObject("usuarioLogado", usuarioLogado);

		List<Disciplina> disciplinasNaoAssociadas = ds.getDisciplinas();
		disciplinasNaoAssociadas.removeAll(professor.getDisciplinas());
		mv.addObject("disciplinas", disciplinasNaoAssociadas);

		return mv;
	}

	// REQUISIÇÃO DE INCLUSÃO DE DISCIPLINA
	@RequestMapping(value = "/professor/disciplinas/{id_professor}/incluir", method = RequestMethod.POST)
	public String associarDisciplina(@ModelAttribute Disciplina disciplina, @PathVariable Long id_professor) {
		Professor professor = ps.getProfessorById(id_professor);
		disciplina = ds.getDisciplinaById(disciplina.getId_disciplina());
		professor.getDisciplinas().add(disciplina);
		ps.salvarProfessor(professor);
		return "redirect:/secretaria/professor/disciplinas/" + id_professor + "?success";
	}

	// VIEW DE CADASTRO DE PROFESSOR
	@RequestMapping(value = "/cadastrar/professor", method = RequestMethod.GET)
	public ModelAndView viewCadastrarProfessor(CadastroProfessorDto professorDto) {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("secretaria/cadastrar-professor");
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// SALVAR PROFESSOR
	@RequestMapping(value = "/cadastrar/professor", method = RequestMethod.POST)
	public String cadastrarProfessor(@ModelAttribute("dto") @Valid CadastroProfessorDto professorDto, BindingResult result,
			@RequestParam("fileUsuario") MultipartFile file, Model model) {
		usuarioLogado();

		Usuario emailExistente = us.usuarioPorEmail(professorDto.getEmail());
		if (emailExistente != null) {
			result.rejectValue("email", null, "Já existe uma conta registrada com este endereço de email.");
		}
		Usuario loginExistente = us.usuarioPorLogin(professorDto.getLogin());
		if (loginExistente != null) {
			result.rejectValue("login", null, "Já existe uma login registrado com este username.");
		}

		if (result.hasErrors()) {
			model.addAttribute("usuarioLogado", usuarioLogado);
			return "secretaria/cadastrar-professor";
		}

		// CRIPTOGRAFANDO A SENHA
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(professorDto.getSenha());
		professorDto.setSenha(encodedPassword);

		List<Role> roles = rs.getRoles();

		cps.salvarProfessorDto(professorDto, file, roles);

		return "redirect:/secretaria/cadastrar/professor?success";
	}
	
	
	
	
	
	
	

	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuarioLogado = us.usuarioPorLogin(login);
		}
	}

}
