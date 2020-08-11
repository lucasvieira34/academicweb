package br.com.academic.dto;

import br.com.academic.validator.FieldMatch;

@FieldMatch(first = "senha", second = "confirmarSenha", message = "As senhas não conferem.")
public class AlterarProfessorDto {
	
	private String senha;
	
	private String confirmarSenha;

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
