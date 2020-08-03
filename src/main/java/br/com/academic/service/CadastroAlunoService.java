package br.com.academic.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.academic.dto.CadastroAlunoDto;
import br.com.academic.models.Aluno;
import br.com.academic.models.AlunoDisciplina;
import br.com.academic.models.AlunoDisciplinaPK;
import br.com.academic.models.Disciplina;
import br.com.academic.models.Role;
import br.com.academic.models.Usuario;
import br.com.academic.repository.AlunoRepository;
import br.com.academic.repository.UsuarioRepository;

@Service
public class CadastroAlunoService {

	@Autowired
	private UsuarioRepository ur;

	@Autowired
	private AlunoRepository ar;
	
	@Autowired
	private AlunoDisciplinaService ads;

	public void salvarAlunoDto(CadastroAlunoDto alunoDto, MultipartFile file, List<Role> roles) {
		Usuario usuario = new Usuario();
		Aluno aluno = new Aluno();
		AlunoDisciplina alunoDisciplina = new AlunoDisciplina();

		usuario.setEmail(alunoDto.getEmail());
		usuario.setNome(alunoDto.getNome());
		usuario.setLogin(alunoDto.getLogin());
		usuario.setSenha(alunoDto.getSenha());

		try {
			usuario.setImagem(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		aluno.setMatricula(alunoDto.getMatricula());
		aluno.setNome(alunoDto.getNome());
		aluno.setSobrenome(alunoDto.getSobrenome());
		aluno.setCpf(alunoDto.getCpf());
		aluno.setDataNascimento(alunoDto.getDataNascimento());
		aluno.setNomeResponsavel(alunoDto.getNomeResponsavel());
		aluno.setEmailResponsavel(alunoDto.getEmailResponsavel());

		aluno.setUsuario(usuario);
		ar.save(aluno);

		for (int i = 1; i < 13; i++) {
			Disciplina disciplina = new Disciplina();
			disciplina.setId_disciplina(i);

			alunoDisciplina.setAluno(aluno);
			alunoDisciplina.setDisciplina(disciplina);

			AlunoDisciplinaPK alunoDisciplinaPk = new AlunoDisciplinaPK(aluno.getId_aluno(),
					disciplina.getId_disciplina());
			alunoDisciplina.setId(alunoDisciplinaPk);

			ads.salvarAlunoDisciplina(alunoDisciplina);
		}

		// SETANDO TODAS AS ROLES
		usuario.setRoles(roles);
		// REMOVENDO A ROLE DE PROFESSOR DA LISTA
		usuario.getRoles().remove(1);
		// REMOVENDO A ROLE DE SECRETARIA DA LISTA
		usuario.getRoles().remove(1);

		ur.save(usuario);

	}

}
