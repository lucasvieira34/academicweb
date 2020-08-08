package br.com.academic.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.academic.dto.PasswordForgotDto;
import br.com.academic.models.Mail;
import br.com.academic.models.ValidationToken;
import br.com.academic.models.Usuario;
import br.com.academic.repository.ValidationTokenRepository;
import br.com.academic.service.EmailService;
import br.com.academic.service.UsuarioService;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private ValidationTokenRepository tokenRepository;
	
	@Autowired
	private EmailService emailService;
	
	@ModelAttribute("forgotPasswordForm")
	public PasswordForgotDto forgotPasswordDto() {
		return new PasswordForgotDto();
	}
	
	@GetMapping
	public String displayForgotPasswordPage() {
		return "password/forgot-password";
	}
	
	@PostMapping
	public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form, 
											BindingResult result, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			return "password/forgot-password";
		}
		
		Usuario usuario = userService.usuarioPorEmail(form.getEmail());
		if (usuario == null) {
			result.rejectValue("email", null, "Não conseguimos encontrar uma conta associada a este email.");
			return "password/forgot-password";
		}
		
		ValidationToken token = new ValidationToken();
		token.setToken(UUID.randomUUID().toString());
		token.setUsuario(usuario);
		token.setExpiryDate(30);
		tokenRepository.save(token);
		
		Mail mail = new Mail();
		mail.setFrom("academicwebboot@gmail.com");
		mail.setTo(usuario.getEmail());
		mail.setSubject("Solicitação para Redefinição de Senha");
		
		Map<String, Object> model = new HashMap<>();
		model.put("token", token);
		model.put("user", usuario);
		
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); 
		model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
		mail.setModel(model);
		emailService.sendEmail(mail);
		
		return "redirect:/forgot-password?success";
		
	}
	
}
