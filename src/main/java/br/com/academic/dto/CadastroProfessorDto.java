package br.com.academic.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CadastroProfessorDto {
	
	@NotEmpty(message = "A matrícula não pode estar vazia.")
	@Size(min = 4, max = 8 ,message = "A matrícula precisa ter entre 4 e 8 números.")
	private String matricula;
	
	@NotEmpty(message = "O nome não pode estar vazio.")
	private String nome;
	
	@NotEmpty(message = "O sobrenome não pode estar vazio.")
	private String sobrenome;
	
	@Email(message = "Este email não é válido.")
	@NotEmpty(message = "O email não pode estar vazio.")
	private String email;
	
	@NotEmpty(message = "O login não pode estar vazio.")
	private String login;
	
	@NotEmpty(message = "A senha não pode estar vazia.")
	private String senha;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
