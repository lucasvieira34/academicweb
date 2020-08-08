package br.com.academic.dto;

import javax.validation.constraints.NotEmpty;

import br.com.academic.validator.FieldMatch;

@FieldMatch.List({
	@FieldMatch(first = "password", second = "confirmPassword", message = "As senhas não conferem."),
	@FieldMatch(first = "confirmPassword", second = "password", message = "As senhas não conferem.")
})
public class PasswordResetDto {
	
	@NotEmpty(message = "A senha não pode estar vazia.")
	private String password;
	
	@NotEmpty(message = "Repita a senha.")
	private String confirmPassword;
	
	@NotEmpty
	private String token;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
