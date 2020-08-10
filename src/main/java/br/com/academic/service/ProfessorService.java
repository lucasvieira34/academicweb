package br.com.academic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academic.models.Professor;
import br.com.academic.repository.ProfessorRepository;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository pr;
	
	public List<Professor> getProfessores(){
		return pr.findAll();
	}
	
	public void salvarProfessor(Professor professor) {
		pr.save(professor);	
	}
	
	public Professor getProfessorById(Long id) {
		return pr.findById(id).get();
	}
	
	public long quantidadeProfessores() {
		return pr.count();
	}
	
}
