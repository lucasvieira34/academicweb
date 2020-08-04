package br.com.academic.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.academic.dto.CadastroAlunoDto;
import br.com.academic.models.Aluno;
import br.com.academic.models.AlunoDisciplina;
import br.com.academic.models.AlunoDisciplinaPK;
import br.com.academic.models.Disciplina;
import br.com.academic.models.Mail;
import br.com.academic.models.Role;
import br.com.academic.models.Usuario;
import br.com.academic.models.ValidationToken;
import br.com.academic.repository.AlunoRepository;
import br.com.academic.repository.UsuarioRepository;
import br.com.academic.repository.ValidationTokenRepository;

@Service
public class CadastroAlunoService {

	@Autowired
	private UsuarioRepository ur;

	@Autowired
	private AlunoRepository ar;
	
	@Autowired
	private AlunoDisciplinaService ads;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ValidationTokenRepository tokenRepository;

	public void salvarAlunoDto(CadastroAlunoDto alunoDto, MultipartFile file, List<Role> roles, HttpServletRequest request) {
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
		
		ValidationToken token = new ValidationToken();
		token.setToken(UUID.randomUUID().toString());
		token.setUsuario(usuario);
		token.setExpiryDate(24*60);
		tokenRepository.save(token);
		
		Mail mail = new Mail();
		mail.setFrom("academicwebboot@gmail.com");
		mail.setTo(usuario.getEmail());
		mail.setSubject("Bem vindo ao Academic Web!");
		
		Map<String, Object> model = new HashMap<>();
		model.put("token", token);
		model.put("user", usuario);
		
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); 
		model.put("ativarCadastro", url + "/ativarCadastro?token=" + token.getToken());
		mail.setModel(model);
		emailService.emailCadastro(mail);

	}

}
