package br.com.academic.dto;

import javax.validation.constraints.Email;

public class AlterarAlunoDto {
	
	@Email(message = "Este email não é válido.")
	private String emailResponsavel;
	
	@Email(message = "Este email não é válido.")
	private String email;
	
	private String senha;

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

}
