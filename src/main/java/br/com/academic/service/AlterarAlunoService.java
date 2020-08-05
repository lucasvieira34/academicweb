package br.com.academic.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.academic.dto.AlterarAlunoDto;
import br.com.academic.models.Aluno;
import br.com.academic.models.Usuario;
import br.com.academic.repository.AlunoRepository;
import br.com.academic.repository.UsuarioRepository;

@Service
public class AlterarAlunoService {
	
	@Autowired
	private UsuarioRepository ur;

	@Autowired
	private AlunoRepository ar;
	
	public void alterarAlunoDto(AlterarAlunoDto alunoDto, MultipartFile file, Usuario usuarioLogado) {
		
		Aluno aluno = usuarioLogado.getAluno();
		
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
		
		aluno.setEmailResponsavel(alunoDto.getEmailResponsavel());
		usuarioLogado.setEmail(alunoDto.getEmail());
		usuarioLogado.setSenha(alunoDto.getSenha());
		
		ar.save(aluno);
		ur.save(usuarioLogado);
	}

}
