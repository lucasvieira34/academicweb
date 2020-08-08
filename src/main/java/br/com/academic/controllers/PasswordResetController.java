package br.com.academic.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.academic.dto.PasswordResetDto;
import br.com.academic.models.ValidationToken;
import br.com.academic.models.Usuario;
import br.com.academic.repository.ValidationTokenRepository;
import br.com.academic.service.UsuarioService;

@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private ValidationTokenRepository tokenRepository;
	
	@ModelAttribute("passwordResetForm")
	public PasswordResetDto passwordReset() {
		return new PasswordResetDto();
	}
	
	@GetMapping
	public String displayResetPasswordPage(@RequestParam(required = false) String token, Model model) {
		
		ValidationToken resetToken = tokenRepository.findByToken(token);
		if (resetToken == null) {
			model.addAttribute("error", "Não foi possível encontrar o token passado.");
		} else if (resetToken.isExpired()) {
			model.addAttribute("error", "Token expirado, por favor, solicite uma nova alteração de senha.");
		} else {
			model.addAttribute("token", resetToken.getToken());
		}
		
		return "password/reset-password";
		
	}
	
	@PostMapping
	@Transactional
	public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form,
									  BindingResult result,
									  RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
			redirectAttributes.addFlashAttribute("passwordResetForm", form);
			return "redirect:/reset-password?token=" + form.getToken();
		}
		
		ValidationToken token = tokenRepository.findByToken(form.getToken());
		Usuario usuario = token.getUsuario();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String updatedPassword = passwordEncoder.encode(form.getPassword());
		userService.updatePassword(updatedPassword, usuario.getId_usuario());
		tokenRepository.delete(token);
		
		return "redirect:/login?resetSuccess";
		
	}

}
