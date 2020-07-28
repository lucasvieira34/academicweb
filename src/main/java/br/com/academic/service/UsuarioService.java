package br.com.academic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academic.models.Usuario;
import br.com.academic.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository ur;
	
	public void salvarUsuario(Usuario usuario) {
		ur.save(usuario);	
	}
	
	public Usuario usuarioPorLogin(String login) {
		return ur.findByLogin(login);
	}
	
	public Usuario usuarioPorEmail(String email) {
		return ur.findByEmail(email);
	}

	public void updatePassword(String password, long id_usuario) {
		ur.updatePassword(password, id_usuario);
	}

}
