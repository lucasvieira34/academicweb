package br.com.academic.controllers;

import java.io.IOException;

import br.com.academic.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.academic.service.AlunoService;
import br.com.academic.service.UsuarioService;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoService as;

	@Autowired
	private UsuarioService us;
	
	private Usuario usuario;

	@RequestMapping(value = "/aluno/perfil", method = RequestMethod.GET)
	public ModelAndView visualizarPerfil() {
		usuarioLogado();
		Aluno aluno = usuario.getAluno();
		ModelAndView mv = new ModelAndView("alunos/alterar_aluno");
		mv.addObject("aluno", aluno);
		return mv;
	}
	
	// REFATORAR UTILIZADO DTO
	@RequestMapping(value = "/aluno/perfil/alterar", method = RequestMethod.POST)
	public String alterarPerfil(@RequestParam("emailResponsavel") String emailResponsavel, @RequestParam("email") String email, @RequestParam("fileUsuario") MultipartFile file) {
		usuarioLogado();
		usuario.setEmail(email);
		Aluno aluno = usuario.getAluno();
		aluno.setEmailResponsavel(emailResponsavel);
		
		try {
			if(file.isEmpty()) {
				usuario.getImagem();
			}else {
				usuario.setImagem(file.getBytes());
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		as.salvarAluno(aluno);
		us.salvarUsuario(usuario);
		
		return "redirect:/aluno/perfil";
	}
	
	@RequestMapping(value = "/aluno/imagem/{id_aluno}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("id_aluno") long id_aluno) {
		Aluno aluno = this.as.getOneAlunoById(id_aluno);
		Usuario usuarioAluno = aluno.getUsuario();
		return usuarioAluno.getImagem();
	}

	private void usuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuario = us.usuarioPorLogin(login);
		}
	}

}
