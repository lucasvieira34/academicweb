package br.com.academic.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class CadastroAlunoDto {
	
	@NotEmpty(message = "A matrícula não pode estar vazia.")
	@Size(min = 4 ,message = "A matrícula precisa ter no mínimo 4 dígitos.")
	private String matricula;
	
	@NotEmpty(message = "O nome não pode estar vazio.")
	private String nome;
	
	@NotEmpty(message = "O sobrenome não pode estar vazio.")
	private String sobrenome;
	
	@NotEmpty(message = "O cpf não pode estar vazio.")
	private String cpf;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date dataNascimento;
	
	@NotEmpty(message = "O nome do responsável não pode estar vazio.")
	private String nomeResponsavel;
	
	@NotEmpty(message = "O email do responsável não pode estar vazio.")
	@Email(message = "Este email não é válido.")
	private String emailResponsavel;
	
	@NotEmpty(message = "O email do aluno não pode estar vazio.")
	@Email(message = "Este email não é válido.")
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

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
