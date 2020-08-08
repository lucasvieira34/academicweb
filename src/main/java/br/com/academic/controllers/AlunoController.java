package br.com.academic.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.academic.dto.AlterarAlunoDto;
import br.com.academic.models.Aluno;
import br.com.academic.models.AlunoDisciplina;
import br.com.academic.models.Usuario;
import br.com.academic.service.AlterarAlunoService;
import br.com.academic.service.UsuarioService;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private UsuarioService us;
	
	@Autowired
	private AlterarAlunoService aas;

	private Usuario usuarioLogado;

	// LISTAR TODAS AS DISCIPLINAS DO ALUNO LOGADO
	@RequestMapping(value = "/disciplinas", method = RequestMethod.GET)
	public ModelAndView minhasDisciplinas() {
		usuarioLogado();

		Aluno aluno = usuarioLogado.getAluno();
		Set<AlunoDisciplina> alunoDisciplina = aluno.getExtratos();

		ModelAndView mv = new ModelAndView("alunos/disciplinas-aluno");
		mv.addObject("usuarioLogado", usuarioLogado);
		mv.addObject("alunoDisciplina", alunoDisciplina);

		return mv;
	}

	// EXIBIR VIEW DO PERFIL DO ALUNO
	@RequestMapping(value = "/perfil", method = RequestMethod.GET)
	public ModelAndView viewMeuPerfil() {
		usuarioLogado();
		Aluno aluno = usuarioLogado.getAluno();
		ModelAndView mv = new ModelAndView("alunos/perfil-aluno");
		mv.addObject("aluno", aluno);
		mv.addObject("usuarioLogado", usuarioLogado);
		return mv;
	}

	// ALTERAR PERFIL DO ALUNO
	@RequestMapping(value = "/perfil/alterar", method = RequestMethod.POST)
	public String alterarPerfil(@Valid AlterarAlunoDto alunoDto, BindingResult result, RedirectAttributes attr,
			@RequestParam("fileUsuario") MultipartFile file, Model model) {

		Usuario emailExistente = us.usuarioPorEmail(alunoDto.getEmail());
		
		if(usuarioLogado.getEmail().equals(alunoDto.getEmail())) {
			alunoDto.setEmail(usuarioLogado.getEmail());
		} else if (emailExistente != null) {
			result.rejectValue("email", null, "Já existe uma conta registrada com este endereço de email.");
		}

		if (result.hasErrors()) {
			Aluno aluno = usuarioLogado.getAluno();
			model.addAttribute("usuarioLogado", usuarioLogado);
			model.addAttribute("aluno", aluno);
			return "redirect:/aluno/perfil?error";
		}
		
		if(usuarioLogado.getAluno().getEmailResponsavel().equals(alunoDto.getEmailResponsavel())) {
			alunoDto.setEmailResponsavel(usuarioLogado.getAluno().getEmailResponsavel());
		}

		// CRIPTOGRAFANDO A SENHA
		if(alunoDto.getSenha().isEmpty()) {
			alunoDto.setSenha(usuarioLogado.getSenha());
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(alunoDto.getSenha());
			alunoDto.setSenha(encodedPassword);
		}
		
		aas.alterarAlunoDto(alunoDto, file, usuarioLogado);
		
		return "redirect:/aluno/perfil?success";
	}
	
	
	
	
	
	
	
	

	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuarioLogado = us.usuarioPorLogin(login);
		}
	}

}
