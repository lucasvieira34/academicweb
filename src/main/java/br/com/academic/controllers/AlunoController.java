package br.com.academic.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
	public String salvarAluno(Aluno aluno, Usuario usuario, AlunoDisciplina alunoDisciplina, @RequestParam("fileUsuario") MultipartFile file) {
		usuarioLogado();
		
		try {
			usuario.setImagem(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//CRIPTOGRAFANDO A SENHA
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(encodedPassword);
		
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
			
			List<Role> roles = rs.getRoles();
			
			//SETANDO TODAS AS ROLES
			usuario.setRoles(roles);
			//REMOVENDO A ROLE DE ALUNO DA LISTA
			usuario.getRoles().remove(1);
			
			us.salvarUsuario(usuario);
		}

		return "redirect:/professor/disciplinas";
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
	@RequestMapping(value = "professor/disciplinas/{id_disciplina}/alunos", method = RequestMethod.GET)
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
	@RequestMapping(value = "/professor/disciplinas/{id_disciplina}/editarAluno/{id_aluno}", method = RequestMethod.GET)
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
	@RequestMapping(value = "/professor/disciplinas/{id_disciplina}/editarAluno/{id_aluno}/alterar", method = RequestMethod.POST)
	public String alterarAluno(AlunoDisciplina alunoDisciplina, @PathVariable("id_aluno") long id_aluno, @PathVariable("id_disciplina") long id_disciplina) {
		
		Aluno aluno = as.getAlunoById(id_aluno);
		Disciplina disciplina = ds.getDisciplinaById(id_disciplina);
		AlunoDisciplinaPK alunoDisciplinaPK = new AlunoDisciplinaPK(id_aluno, id_disciplina);
		alunoDisciplina.setId(alunoDisciplinaPK);
		alunoDisciplina.setAluno(aluno);
		alunoDisciplina.setDisciplina(disciplina);
		
		ads.salvarAlunoDisciplina(alunoDisciplina);

		return "redirect:/professor/disciplinas/{id_disciplina}/alunos";
	}
	
	@RequestMapping(value = "/aluno/perfil", method = RequestMethod.GET)
	public ModelAndView visualizarPerfil() {
		usuarioLogado();
		Aluno aluno = usuario.getAluno();
		ModelAndView mv = new ModelAndView("alunos/alterar_aluno");
		mv.addObject("aluno", aluno);
		return mv;
	}
	
	@RequestMapping(value = "/aluno/perfil/alterar", method = RequestMethod.POST)
	public String alterarPerfil(@RequestParam("fileUsuario") MultipartFile file) {
		usuarioLogado();
		
		try {
			usuario.setImagem(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		us.salvarUsuario(usuario);
		
		return "redirect:/aluno/perfil";
	}
	
	@RequestMapping(value = "/imagem/{id_aluno}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("id_aluno") long id_aluno) {
		Aluno aluno = this.as.getOneAlunoById(id_aluno);
		Usuario usuario = aluno.getUsuario();
		return usuario.getImagem();
	}

	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuario = us.usuarioPorLogin(login);
		}
	}

}
