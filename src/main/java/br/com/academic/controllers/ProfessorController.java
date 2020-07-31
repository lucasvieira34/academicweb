package br.com.academic.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.dto.CadastroProfessorDto;
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
	
	private Usuario usuarioLogado;
	
	@ModelAttribute("dto")
	public CadastroProfessorDto cadastroProfessorDto() {
		return new CadastroProfessorDto();
	}

	// TEMPLATE CADASTRO DE PROFESSOR
	@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.GET)
	public ModelAndView novoProfessor(CadastroProfessorDto professorDto) {
		usuarioLogado();
		ModelAndView mv = new ModelAndView("professor/novo_professor");
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// SALVAR PROFESSOR
	@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.POST)
	public String salvarProfessor(@ModelAttribute("dto") @Valid CadastroProfessorDto professorDto, BindingResult result, @RequestParam("fileUsuario") MultipartFile file) {
		usuarioLogado();
		
		Usuario emailExistente = us.usuarioPorEmail(professorDto.getEmail());
		if (emailExistente != null) {
			result.rejectValue("email", null, "Já existe uma conta registrada com este endereço de email.");
		}
		Usuario loginExistente = us.usuarioPorLogin(professorDto.getLogin());
		if (loginExistente != null) {
			result.rejectValue("login", null, "Já existe uma login registrado com este username.");
		}
		
		if(result.hasErrors()) {
			return "professor/novo_professor";
		}
		
		//CRIPTOGRAFANDO A SENHA
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(professorDto.getSenha());
		professorDto.setSenha(encodedPassword);
		
		Usuario usuario = new Usuario();
		Professor professor = new Professor();
		
		usuario.setEmail(professorDto.getEmail());
		usuario.setNome(professorDto.getNome());
		usuario.setLogin(professorDto.getLogin());
		usuario.setSenha(professorDto.getSenha());
		
		try {
			usuario.setImagem(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		professor.setMatricula(professorDto.getMatricula());
		professor.setNome(professorDto.getNome());
		professor.setSobrenome(professorDto.getSobrenome());
		
		professor.setUsuario(usuario);
		ps.salvarProfessor(professor);
				
		List<Role> roles = rs.getRoles();
		
		//SETANDO TODAS AS ROLES
		usuario.setRoles(roles);
		//REMOVENDO A ROLE DE ALUNO DA LISTA
		usuario.getRoles().remove(0);
		
		us.salvarUsuario(usuario);
				
		return "redirect:/cadastrarProfessor?success";
	}

	// LISTAR PROFESSORES
	@RequestMapping(value = "/professores", method = RequestMethod.GET)
	public ModelAndView listarProfessores() {

		usuarioLogado();
		ModelAndView mv = new ModelAndView("professor/listar_professores");
		mv.addObject("professores", ps.getProfessores());
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// EDITAR PROFESSOR
	@RequestMapping(value = "/editarProfessor/{id_professor}", method = RequestMethod.GET)
	public ModelAndView editarProfessor(@PathVariable("id_professor") long id) {
		usuarioLogado();
		Professor professor = ps.getProfessorById(id);

		ModelAndView mv = new ModelAndView("professor/associar_disciplina");
		mv.addObject("professor", professor);
		mv.addObject("usuarioLogado", usuarioLogado);
		
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
			usuarioLogado = us.usuarioPorLogin(login);
		}
	}

}
