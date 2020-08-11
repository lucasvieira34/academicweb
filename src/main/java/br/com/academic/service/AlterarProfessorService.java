package br.com.academic.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.academic.dto.AlterarProfessorDto;
import br.com.academic.models.Usuario;
import br.com.academic.repository.UsuarioRepository;

@Service
public class AlterarProfessorService {
	
	@Autowired
	private UsuarioRepository ur;

	public void alterarProfessorDto(AlterarProfessorDto professorDto, MultipartFile file, Usuario usuarioLogado) {
		
		try {
			if(file.isEmpty()) {
				usuarioLogado.getImagem();
			}else {
				usuarioLogado.setImagem(file.getBytes());
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		usuarioLogado.setSenha(professorDto.getSenha());
		
		ur.save(usuarioLogado);
	}

}
