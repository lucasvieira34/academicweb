package br.com.academic.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.academic.dto.CadastroProfessorDto;
import br.com.academic.models.Professor;
import br.com.academic.models.Role;
import br.com.academic.models.Usuario;
import br.com.academic.repository.ProfessorRepository;
import br.com.academic.repository.UsuarioRepository;

@Service
public class CadastroProfessorService {
	
	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private ProfessorRepository pr;
	
	public void salvarProfessorDto(CadastroProfessorDto professorDto, MultipartFile file, List<Role> roles) {
		Usuario usuario = new Usuario();
		Professor professor = new Professor();
		
		usuario.setEmail(professorDto.getEmail());
		usuario.setNome(professorDto.getNome());
		usuario.setLogin(professorDto.getLogin());
		usuario.setSenha(professorDto.getSenha());
		
		try {
			usuario.setImagem(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		professor.setMatricula(professorDto.getMatricula());
		professor.setNome(professorDto.getNome());
		professor.setSobrenome(professorDto.getSobrenome());
		professor.setCpf(professorDto.getCpf());
		professor.setDataNascimento(professorDto.getDataNascimento());
		
		professor.setUsuario(usuario);
		pr.save(professor);
		
		//SETANDO TODAS AS ROLES
		usuario.setRoles(roles);
		//REMOVENDO A ROLE DE ALUNO DA LISTA
		usuario.getRoles().remove(0);
		//REMOVENDO A ROLE DE SECRETARIA DA LISTA
		usuario.getRoles().remove(1);
		
		ur.save(usuario);
		
	}

}
