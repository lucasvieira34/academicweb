package br.com.academic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.academic.dto.CadastroProfessorDto;
import br.com.academic.models.Professor;
import br.com.academic.models.Role;
import br.com.academic.models.Usuario;
import br.com.academic.repository.ProfessorRepository;
import br.com.academic.repository.RoleRepository;
import br.com.academic.repository.UsuarioRepository;

@Service
public class CadastroProfessorService {
	
	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private ProfessorRepository pr;
	
	@Autowired
	private RoleRepository rr;
	
	public void salvarProfessorDto(CadastroProfessorDto professorDto, MultipartFile file) {
		Usuario usuario = new Usuario();
		Professor professor = new Professor();
		
		Role role = rr.findByNome("ROLE_PROFESSOR");
		usuario.setRoles(new ArrayList<Role>(Arrays.asList(role)));
		
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
		professor.setSalario(professorDto.getSalario());
		
		professor.setUsuario(usuario);
		
		pr.save(professor);
		ur.save(usuario);
		
	}

}
