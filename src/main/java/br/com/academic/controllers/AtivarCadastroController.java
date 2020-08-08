package br.com.academic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.academic.models.Usuario;
import br.com.academic.models.ValidationToken;
import br.com.academic.repository.ValidationTokenRepository;
import br.com.academic.service.UsuarioService;

@Controller
@RequestMapping("/ativarCadastro")
public class AtivarCadastroController {

	@Autowired
	private UsuarioService userService;

	@Autowired
	private ValidationTokenRepository tokenRepository;

	@GetMapping
	@Transactional
	public String ativarCadastro(@RequestParam(required = false) String token) {

		ValidationToken tokenCadastro = tokenRepository.findByToken(token);
		
		if (tokenCadastro == null) {
			return "redirect:/login?tokenInvalido";
		}

		if (tokenCadastro.isExpired()) {
			return "redirect:/login?tokenExpirado";
		}
		
		Usuario usuario = tokenCadastro.getUsuario();
		usuario.setAtivo(true);
		userService.salvarUsuario(usuario);
		tokenRepository.delete(tokenCadastro);
		
		return "redirect:/login?enable";

	}

}
