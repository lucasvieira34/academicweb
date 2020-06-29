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

}
