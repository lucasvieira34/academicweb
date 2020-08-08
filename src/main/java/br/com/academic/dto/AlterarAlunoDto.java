package br.com.academic.dto;

import javax.validation.constraints.Email;

import br.com.academic.validator.FieldMatch;

@FieldMatch(first = "senha", second = "confirmarSenha", message = "As senhas não conferem.")
public class AlterarAlunoDto {
	
	@Email(message = "Este email não é válido.")
	private String emailResponsavel;
	
	@Email(message = "Este email não é válido.")
	private String email;
	
	private String senha;
	
	private String confirmarSenha;

	public String getEmailResponsavel() {
		return emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

}
