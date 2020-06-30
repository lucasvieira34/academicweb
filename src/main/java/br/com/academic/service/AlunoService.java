package br.com.academic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academic.models.Aluno;
import br.com.academic.repository.AlunoRepository;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository ar;
	
	public List<Aluno> getAlunos(){
		return ar.findAll();
	}
	
	public void salvarAluno(Aluno aluno) {
		ar.save(aluno);	
	}
	
	public Aluno getAlunoById(Long id) {
		return ar.findById(id).get();
	}

}